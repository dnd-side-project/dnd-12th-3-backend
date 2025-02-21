// package com.dnd.backend.notification.firebase;
//
// import java.io.FileInputStream;
// import java.io.IOException;
//
// import org.springframework.context.annotation.Configuration;
//
// import com.google.auth.oauth2.GoogleCredentials;
// import com.google.firebase.FirebaseApp;
// import com.google.firebase.FirebaseOptions;
//
// import jakarta.annotation.PostConstruct;
//
// @Configuration
// public class FirebaseConfig {
//
// 	@PostConstruct
// 	public void initialize() {
// 		try {
// 			FileInputStream serviceAccount =
// 				new FileInputStream("src/main/resources/watchout-b06e5-firebase-adminsdk-fbsvc-4164719075.json");
//
// 			FirebaseOptions options = FirebaseOptions.builder()
// 				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
// 				.build();
//
// 			FirebaseApp.initializeApp(options);
// 		} catch (IOException e) {
// 			e.printStackTrace();
// 		}
// 	}
// }
