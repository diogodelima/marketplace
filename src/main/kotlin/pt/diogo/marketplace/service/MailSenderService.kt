package pt.diogo.marketplace.service

import org.springframework.mail.SimpleMailMessage
import org.springframework.stereotype.Service
import pt.diogo.marketplace.config.MailConfig

@Service
class MailSenderService(

    private val mailConfig: MailConfig

) {

    fun send(to: String, subject: String, body: String){

        val message = SimpleMailMessage()
        message.from = "noreply@marketplace.pt"
        message.setTo(to)
        message.subject = subject
        message.text = body
        mailConfig.getJavaMailSender().send(message)

    }

}