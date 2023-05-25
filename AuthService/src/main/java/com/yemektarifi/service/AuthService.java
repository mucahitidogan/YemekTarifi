package com.yemektarifi.service;

import com.yemektarifi.dto.request.*;
import com.yemektarifi.dto.response.RegisterResponseDto;
import com.yemektarifi.exception.AuthManagerException;
import com.yemektarifi.exception.ErrorType;
import com.yemektarifi.manager.IUserProfileManager;
import com.yemektarifi.mapper.IAuthMapper;
import com.yemektarifi.rabbitmq.model.ForgotPasswordMailModel;
import com.yemektarifi.rabbitmq.producer.ActivateCodeMailProducer;
import com.yemektarifi.rabbitmq.producer.ForgotPasswordMailProducer;
import com.yemektarifi.repository.IAuthRepository;
import com.yemektarifi.repository.entity.Auth;
import com.yemektarifi.repository.entity.enums.EStatus;
import com.yemektarifi.utility.JwtTokenProvider;
import com.yemektarifi.utility.ServiceManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.yemektarifi.utility.CodeGenerator.generateCode;

@Service
public class AuthService extends ServiceManager<Auth, Long> {

    private final IAuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final IUserProfileManager userProfileManager;
    private final ActivateCodeMailProducer activateCodeMailProducer;
    private final ForgotPasswordMailProducer forgotPasswordMailProducer;


    public AuthService(IAuthRepository authRepository, PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider,
                       IUserProfileManager userProfileManager, ActivateCodeMailProducer activateCodeMailProducer,
                       ForgotPasswordMailProducer forgotPasswordMailProducer) {
        super(authRepository);
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.userProfileManager = userProfileManager;
        this.activateCodeMailProducer = activateCodeMailProducer;
        this.forgotPasswordMailProducer = forgotPasswordMailProducer;
    }

    public RegisterResponseDto register(RegisterRequestDto dto){
        Optional<Auth> optionalAuth = authRepository.findOptionalByEmail(dto.getEmail());
        if(optionalAuth.isPresent())
            throw new AuthManagerException(ErrorType.THIS_EMAIL_IS_ALREADY_REGISTER);
        Auth auth = IAuthMapper.INSTANCE.fromRegisterRequestDtoToAuth(dto);
        if(dto.getPassword().equals(dto.getRePassword())){
            auth.setActivationCode(generateCode());
            auth.setPassword(passwordEncoder.encode(dto.getPassword()));
            save(auth);
            userProfileManager.createUser(IAuthMapper.INSTANCE.fromAuthToCreateUserProfile(auth));
            activateCodeMailProducer.sendActivationCode(IAuthMapper.INSTANCE.fromAuthToActivateCodeMailProducer(auth));
        }else{
            throw new AuthManagerException(ErrorType.PASSWORD_ERROR);
        }
        RegisterResponseDto registerResponseDto = IAuthMapper.INSTANCE.fromAuthToRegisterResponseDto(auth);
        return registerResponseDto;
    }

    public List<Auth> findAll(){
        return findAll();
    }

    public Boolean activateStatus(ActivateStatusRequestDto dto){
        Optional<Auth> optionalAuth = findById(dto.getAuthId());
        if(optionalAuth.isPresent()){
            if (dto.getActivateCode().equals(optionalAuth.get().getActivationCode())){
                optionalAuth.get().setStatus(EStatus.ACTIVE);
                save(optionalAuth.get());
                userProfileManager.activateStatus(dto.getAuthId());
                return true;
            }
            else
                throw new AuthManagerException(ErrorType.ACTIVATE_CODE_ERROR);
        }else
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
    }

    public String login(LoginRequestDto dto){
        Optional<Auth> optionalAuth = authRepository.findOptionalByUsername(dto.getUsername());
        if(optionalAuth.isEmpty() || !passwordEncoder.matches(dto.getPassword(), optionalAuth.get().getPassword()))
            throw new AuthManagerException(ErrorType.LOGIN_ERROR);
        else if (!optionalAuth.get().getStatus().equals(EStatus.ACTIVE))
            throw new AuthManagerException(ErrorType.ACTIVATE_CODE_ERROR);
        Optional<String> token = tokenProvider.createToken(optionalAuth.get().getAuthId(),
                                                            optionalAuth.get().getRole());
        return token.orElseThrow(() -> {throw new AuthManagerException(ErrorType.TOKEN_NOT_CREATED);});
    }

    public Boolean changePassword(FromUserProfileChangePasswordRequestDto dto){
        Optional<Auth> optionalAuth = findById(dto.getAuthId());
        System.out.println(optionalAuth.get());
        if(optionalAuth.isEmpty())
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        optionalAuth.get().setPassword(dto.getPassword());
        save(optionalAuth.get());
        return true;
    }

    public Boolean forgotPassword(String email, String username) {
        Optional<Auth> optionalAuth = authRepository.findOptionalByEmail(email);
        if (optionalAuth.get().getStatus().equals(EStatus.ACTIVE)) {
            if (optionalAuth.get().getUsername().equals(username)) {
                String randomPassword = UUID.randomUUID().toString();
                optionalAuth.get().setPassword(passwordEncoder.encode(randomPassword));
                save(optionalAuth.get());
                ForgotPasswordMailModel model = ForgotPasswordMailModel.builder()
                        .password(randomPassword)
                        .email(email)
                        .build();
                forgotPasswordMailProducer.sendForgotPasswordMail(model);
                ToUserProfileServiceForgotPasswordUpdateRequestDto forgotPasswordUpdateRequestDto = ToUserProfileServiceForgotPasswordUpdateRequestDto.builder()
                        .authId(optionalAuth.get().getAuthId())
                        .password(optionalAuth.get().getPassword())
                        .build();
                userProfileManager.forgotPassword(forgotPasswordUpdateRequestDto);
                return true;
            } else {
                throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
            }
        } else {
            if (optionalAuth.get().getStatus().equals(EStatus.DELETED))
                throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
            throw new AuthManagerException(ErrorType.ACTIVATE_CODE_ERROR);
        }
    }

    public Boolean updateAuth(FromUserProfileServiceUpdateAuthRequestDto dto) {
        Optional<Auth> optionalAuth = findById(dto.getAuthId());
        if (optionalAuth.isEmpty())
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        Auth auth = IAuthMapper.INSTANCE.fromUserProfileServiceUpdateAuthRequestDtoToAuth(dto, optionalAuth.get());
        update(auth);
        return true;
    }

    public Boolean deleteByAuthId(Long authId) {
        Optional<Auth> optionalAuth = findById(authId);
        if(optionalAuth.isEmpty())
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        else if(!optionalAuth.get().getStatus().equals(EStatus.DELETED)){
            optionalAuth.get().setStatus(EStatus.DELETED);
            update(optionalAuth.get());
            return true;
        }else{
            return false;
        }
    }
}
