import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class saveRetrieveImg {
    public Connection con;


    public static void main(String[] args) {
        new saveRetrieveImg();
    }

    public saveRetrieveImg() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/imgtable", "root", "root");
            saveImg();
            System.out.println("do you want to retrieve images?(y|n)");
            Scanner reader = new Scanner(System.in);

            char inchar =reader.next().charAt(0);
            if(inchar == 'y'){
                retrieveImg();
                System.out.println("ok");
            }
            else
            {
            con.close();}
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void saveImg() {

        try {


            PreparedStatement pstmt = con.prepareStatement("insert into keepImg values (?, ?)");
            pstmt.setString(1, "sm");
            FileInputStream fin = new FileInputStream("/home/shaghayegh/Desktop/sm.jpg");
            pstmt.setBinaryStream(2, fin, fin.available());
            int i = pstmt.executeUpdate();
            System.out.println(i + " records affected.image is saved");


        } catch (SQLException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }

    }
    public void retrieveImg(){
        try {

        PreparedStatement ps=con.prepareStatement("select * from keepImg");
        ResultSet rs=ps.executeQuery();
        if(rs.next()){//now on 1st row

            Blob b=rs.getBlob(2);//2 means 2nd column data
            byte barr[]=b.getBytes(1,(int)b.length());//1 means first image

            FileOutputStream fout=new FileOutputStream("/home/shaghayegh/Desktop/sonoo.jpg");
            fout.write(barr);
        }
        } catch (SQLException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }


}
}
