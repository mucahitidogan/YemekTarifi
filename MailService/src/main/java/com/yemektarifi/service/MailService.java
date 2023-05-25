package com.yemektarifi.service;


import com.yemektarifi.rabbitmq.model.ActivateCodeMailModel;
import com.yemektarifi.rabbitmq.model.FavoriteCategoryMailModel;
import com.yemektarifi.rabbitmq.model.ForgotPasswordMailModel;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;

    public Boolean sendActivateCode(ActivateCodeMailModel activateCodeMailModel){
        try{
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("${spring.mail.username}");
            mailMessage.setTo(activateCodeMailModel.getEmail());
            mailMessage.setSubject("ACTIVATION CODE MAIL");
            mailMessage.setText("Activation Code: " + activateCodeMailModel.getActivationCode()+
                    "\n\nAfter activating, you can log in to your account.");
            javaMailSender.send(mailMessage);
        }catch (Exception e){
            e.getMessage();
        }
        return true;
    }

    public Boolean sendMailForgetPassword(ForgotPasswordMailModel forgotPasswordMailModel) {
        try{
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("${spring.mail.username}");
            mailMessage.setTo(forgotPasswordMailModel.getEmail());
            mailMessage.setSubject("PASSWORD RESET EMAIL");
            mailMessage.setText("Your New Password: " + forgotPasswordMailModel.getPassword() +
                    "\n\nFor security reasons, change your password after logging in.");
            javaMailSender.send(mailMessage);
        }catch (Exception e){
            e.getMessage();
        }
        return true;
    }

    public Boolean sendMailFavoriteCategory(FavoriteCategoryMailModel model) {
        try{
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            model.getRequestDtoList().forEach(userInfo ->{
                int i = 0;
                mailMessage.setFrom("${spring.mail.username}");
                mailMessage.setTo(userInfo.getEmail());
                mailMessage.setSubject("A NEW RECIPE IS ADDED THAT MAY INTEREST YOU");
                String text = "";
                text = "Dear " + userInfo.getName() + " " + userInfo.getSurname() +
                        "\n\nWe checked your favorite recipes and send this mail. This new recipe may interest you." +
                        "\n\nRecipe Name: "  + model.getRecipeName() +
                        "\n\nRecipe Categories: ";
                        for(String categoryName : model.getCategoryNameList()){
                            text += " " + categoryName;
                }
                mailMessage.setText(text);
                javaMailSender.send(mailMessage);

            });
        }catch (Exception e){
            e.getMessage();
        }
        return true;
    }

}


