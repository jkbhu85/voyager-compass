package com.jk.vc.mail;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mailer {
	private static Mailer uniqueInstance = null;
	private static final String SENDER = "voyager.compass@gmail.com";
	private static final String PASSWORD = "56781234";


	private Mailer() {
	}


	public static Mailer getInstance() {
		// thread safe implementation
		if (uniqueInstance == null) {
			synchronized (Mailer.class) {
				if (uniqueInstance == null) {
					uniqueInstance = new Mailer();
				}
			}
		}

		return uniqueInstance;
	}


	public boolean sendMail(String to, String subject, String text) {
		boolean status = false;
		Properties props = new Properties();

		// setting mail host
		props.setProperty("mail.smtp.host", "smtp.gmail.com");
		props.setProperty("mail.smtp.port", "587");
		// Transport Layer Security enabled
		props.setProperty("mail.smtp.starttls.enable", "true");
		props.setProperty("mail.user", SENDER);

		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.password", PASSWORD);

		Authenticator auth = new Authenticator() {
			@Override
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(SENDER, PASSWORD);
			}
		};

		Session session = Session.getInstance(props, auth);

		MimeMessage mail = new MimeMessage(session);

		try {
			mail.setFrom(new InternetAddress(SENDER));
			mail.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
			mail.setSubject(subject);
			mail.setText(text);

			Transport.send(mail);
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;

	}
}
