package org.example;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;

import java.io.File;
import java.util.Collections;

public class ServidorFTP {
    public static void main(String[] args) {
        try {
            // Crear la fábrica del servidor
            FtpServerFactory serverFactory = new FtpServerFactory();
            ListenerFactory listenerFactory = new ListenerFactory();

            // Configurar puerto (21 por defecto)
            listenerFactory.setPort(5000);
            serverFactory.addListener("default", listenerFactory.createListener());

            // Configuración de usuarios
            PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
            userManagerFactory.setFile(new File("usuarios.properties")); // Archivo de usuarios
            UserManager userManager = userManagerFactory.createUserManager();

            // Crear usuario "admin"
            BaseUser user = new BaseUser();
            user.setName("admin");
            user.setPassword("admin");
            user.setHomeDirectory("/home/alumno/IdeaProjects/FTP");
            user.setAuthorities(Collections.singletonList(new WritePermission()));

            userManager.save(user);
            serverFactory.setUserManager(userManager);

            // Iniciar el servidor FTP
            FtpServer server = serverFactory.createServer();
            server.start();
            System.out.println("Servidor FTP iniciado en el puerto 5000...");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
