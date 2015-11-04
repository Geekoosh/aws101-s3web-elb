import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class CopyS3Servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String urlParam = req.getParameter("url");
        String nameParam = req.getParameter("name");

        AmazonS3Client amazonS3Client = new AmazonS3Client(new DefaultAWSCredentialsProviderChain());
        URL url = new URL(urlParam);
        InputStream in = new BufferedInputStream(url.openStream());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType("image/jpeg");
        PutObjectRequest putObjectRequest = new PutObjectRequest("wiseman-bucket-test", nameParam, in, objectMetadata);
        putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
        PutObjectResult result = amazonS3Client.putObject(putObjectRequest);
        resp.getWriter().print(amazonS3Client.getResourceUrl("wiseman-bucket-test", nameParam));
    }
}
