import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class Payments {

    private static Scanner sc = new Scanner(System.in);

    private static int customerNumber;
    private static String checkNumber;
    private static Date paymentDate;
    private static double amount;
    private static String inputDate;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");

    public static void createPayment(){
        System.out.println("===========================");
        System.out.println("       CREATE PAYMENT      ");
        System.out.println("===========================\n");

        try {  
            int stopper = 1;
            do{
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbsales","root","12345");

                System.out.print("Enter customer number: ");
                customerNumber = sc.nextInt();
                                sc.nextLine();

                String payQuery = "SELECT * FROM payments p JOIN customers c on p.customerNumber =  c.customerNumber WHERE p.customerNumber = ?";
                PreparedStatement ps1 = con.prepareStatement(payQuery);
                ps1.setInt(1, customerNumber);
                ResultSet rs1 = ps1.executeQuery();
                int payCount = 0;
                while (rs1.next()){
                    payCount ++;
                }

                System.out.println("TEST1 " + payCount);
    
                String orderQuery = "SELECT * FROM orders o JOIN customers c on o.customerNumber =  c.customerNumber WHERE o.customerNumber = ?";
                PreparedStatement ps2 = con.prepareStatement(orderQuery);
                ps2.setInt(1, customerNumber);
                ResultSet rs2 = ps2.executeQuery();
                int orderCount = 0;
                while (rs2.next()){
                    orderCount ++;
                }
                System.out.println("TEST2 " + orderCount);

                if (payCount < orderCount){
                    System.out.print("Enter check number: ");
                    checkNumber = sc.nextLine();

                    System.out.print("Enter payment date (yyyy-mm-dd): ");
                    inputDate = sc.nextLine();

                    System.out.print("Enter amount: ");
                    amount = sc.nextDouble();

                    paymentDate = Date.valueOf(inputDate);

                    String add = "INSERT INTO payments " + "(customerNumber, checkNumber, paymentDate, amount)" +  
                                                            "VALUES (?, ?, ?, ?)";
                    PreparedStatement ps = con.prepareStatement(add);

                    ps.setInt(1, customerNumber);
                    ps.setString(2, checkNumber);
                    ps.setDate(3, paymentDate);
                    ps.setDouble(4, amount);

                    int x = ps.executeUpdate();

                    if (x > 0){
                        System.out.println("\nSuccessfully inserted\n");
                    } else{
                        System.out.println("Not inserted\n");
                    }

                    System.out.print("Insert another record (Y/N): ");
                    char stop = sc.next().charAt(0);

                    if (stop == 'n' || stop == 'N'){
                        stopper = 0;
                    } else {
                        System.out.println();
                    }

                    ps.close();
                } else {
                    System.out.println("No Pending Payments");
                }  
                ps1.close();
                ps2.close();
                con.close();
            } while (stopper == 1);
        } catch (Exception e){
            System.out.println();
        }
    }

    public static void updatePayment(){

        System.out.println("===========================");
        System.out.println("       UPDATE PAYMENT      ");
        System.out.println("===========================\n");

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbsales","root","12345");

            String selectCol = "SELECT * FROM payments WHERE checkNumber = ?";
            PreparedStatement ps = con.prepareStatement(selectCol);

            System.out.print("Enter check number: ");
            checkNumber = sc.nextLine();

            ps.setString(1, checkNumber);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                System.out.println("customerNumber: " + rs.getInt("customerNumber"));
                System.out.println("checkNumber: " + rs.getString("checkNumber"));
                System.out.println("paymentDate: " + rs.getString("paymentDate"));
                System.out.println("amount: " + rs.getString("amount"));

                System.out.print("\nWant to update this record? (Y/N): ");
                char choice = sc.next().charAt(0);

                if (choice == 'y' || choice == 'Y'){

                    int stopper = 1;
                    do{
                        System.out.print("Enter column name to update: ");
                        String col_name = sc.next();
                        System.out.print("Enter updated value: ");
                        String new_value = sc.next();
                 
                        String updateQuery = "UPDATE payments SET " + col_name + " = ? " + "WHERE  checkNumber = ?";
                        PreparedStatement updatePs = con.prepareStatement(updateQuery);
                        updatePs.setString(1, new_value);     
                        updatePs.setString(2, checkNumber);

                        int x = updatePs.executeUpdate(); 

                        if(x == 1){
                            System.out.println("\nRecord updated\n");
                            System.out.print("Do you still want to keep updating the record "+ checkNumber +" (Y/N): ");
                            char stop = sc.next().charAt(0);

                            if(stop == 'n' || stop == 'N'){
                                stopper = 0;
                            } 
                        } else {System.out.println("Error updating\n");}
                        updatePs.close();
                    } while (stopper == 1); 
                } else {
                    System.out.println("\nNot updating...\n");
                }
            } else { 
                System.out.println("\nRecord does not exist. Going back to main menu...\n");
            }
            ps.close();
            con.close();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void deletePayment(){
        System.out.println("===========================");
        System.out.println("       DELETE PAYMENT      ");
        System.out.println("===========================\n");

        int stopper = 1;
        do{
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbsales","root","12345");
                
                System.out.print("Enter column to search for value of deletion: ");
                String col_name = sc.next();
    
                System.out.print("Enter value to delete: ");
                String value = sc.next();

                String delete = "DELETE FROM payments WHERE " + col_name + "= ?";
                PreparedStatement ps = con.prepareStatement(delete);

                ps.setString(1, value);

                int x = ps.executeUpdate();

                if(x > 0){
                    System.out.println("Record " + value +" deleted successfully\n");
                } else {
                    System.out.println("Record does not exist or cannot be deleted\n");
                }

                System.out.println("Do you want to delete another record? (Y/N):  ");
                char stop = sc.next().charAt(0);

                if(stop == 'n' || stop == 'N'){
                    stopper = 0;
                }
                ps.close();
                con.close();
            } catch(SQLException e){
                if(e.getErrorCode() == 1451){
                    System.out.println("Error: Cannot delete record because it is being used as a reference by other records");
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } while (stopper == 1);
    }
}
