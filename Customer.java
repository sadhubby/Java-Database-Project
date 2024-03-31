import java.sql.*;
import java.util.Scanner;

public class Customer {
    private static Scanner sc = new Scanner(System.in);

    private static int customerNo;
    private static String customerName;
    private static String contactLastName;
    private static String contactFirstName;
    private static String phone;
    private static String addressLine1;
    private static String addressLine2;
    private static String city;
    private static String state;
    private static String postalCode;
    private static String country;
    private static Integer salesRepEmpNo;
    private static double creditLimit;

    public static void createCustomer(){
        System.out.println("===========================");
        System.out.println("      CREATE CUSTOMER      ");
        System.out.println("===========================\n");

        int stopper = 1;
        do{    
            System.out.print("Enter customer number: ");
            customerNo = sc.nextInt();
                        sc.nextLine();
            
            System.out.print("Enter customer name: ");
            customerName = sc.nextLine();

            System.out.print("Enter contact's last name: ");
            contactLastName = sc.nextLine();

            System.out.print("Enter contact's first name: ");
            contactFirstName = sc.nextLine();

            System.out.print("Enter contact number: ");
            phone = sc.nextLine();

            System.out.print("Enter address line 1: ");
            addressLine1 = sc.nextLine();

            System.out.print("Enter address line 2: ");
            addressLine2 = sc.nextLine();

            System.out.print("Enter city: ");
            city = sc.nextLine();

            System.out.print("Enter state: ");
            state = sc.nextLine();

            System.out.print("Enter postal code: ");
            postalCode = sc.nextLine();

            System.out.print("Enter country: ");
            country = sc.nextLine();

            System.out.print("Enter sales representative number: ");
            try {   
                salesRepEmpNo = Integer.parseInt(sc.nextLine());
             } catch (NumberFormatException nfe) {
                salesRepEmpNo = null;
             }

            System.out.print("Enter credit limit: ");
            creditLimit = sc.nextDouble();

            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbsales","root","12345");

                PreparedStatement ps;
                String add = "INSERT INTO customers " + "(customerNumber, customerName, contactLastName, contactFirstName, phone," +  
                                                        "addressLine1, addressLine2, city, state, postalCode, country, salesRepEmployeeNumber, creditLimit) " + 
                                                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                ps = con.prepareStatement(add);

                ps.setInt(1, customerNo);
                ps.setString(2, customerName);
                ps.setString(3, contactLastName);
                ps.setString(4, contactFirstName);
                ps.setString(5, phone);
                ps.setString(6, addressLine1);
                ps.setObject(7, addressLine2, Types.VARCHAR);
                ps.setString(8, city);
                ps.setObject(9, state, Types.VARCHAR);
                ps.setObject(10, postalCode, Types.VARCHAR);
                ps.setString(11, country);
                ps.setObject(12, salesRepEmpNo, Types.INTEGER);
                ps.setDouble(13, creditLimit);

                int x = ps.executeUpdate();

                if (x > 0){
                    System.out.println("\nSuccessfully inserted\n");
                } else{
                    System.out.println("Not inserted\n");
                }

                ps.close();
                con.close();
            } catch (Exception e){
                System.out.println(e.getMessage());
            }

            System.out.print("Insert another record (Y/N): ");
            char stop = sc.next().charAt(0);

            if (stop == 'n' || stop == 'N'){
                stopper = 0;
            } else {
                System.out.println();
            }
        } while (stopper == 1);
    }

    public static void updateCustomer(){

        System.out.println("===========================");
        System.out.println("      UPDATE CUSTOMER      ");
        System.out.println("===========================\n");

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbsales","root","12345");
        
            String selectCol = "SELECT * FROM customers WHERE customerNumber = ?";
            PreparedStatement ps = con.prepareStatement(selectCol);

            System.out.print("Enter customer number: ");
            customerNo = sc.nextInt();
                            sc.nextLine();

            ps.setInt(1, customerNo);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                System.out.println("customerNumber: " + rs.getInt("customerNumber"));
                System.out.println("customerName: " + rs.getString("customerName"));
                System.out.println("contactLastName: " + rs.getString("contactLastName"));
                System.out.println("contactFirstName: " + rs.getString("contactFirstName"));
                System.out.println("phone: " + rs.getString("phone"));
                System.out.println("addressLine1: " + rs.getString("addressLine1"));
                System.out.println("addressLine2: " + rs.getObject("addressLine2"));
                System.out.println("city: " +  rs.getString("city"));
                System.out.println("state: " + rs.getObject("state"));
                System.out.println("postalCode: " + rs.getObject("postalCode"));
                System.out.println("country: " + rs.getString("country"));
                System.out.println("salesRepEmployeeNumber: " + rs.getObject("salesRepEmployeeNumber"));
                System.out.println("creditLimit: " + rs.getDouble("creditLimit"));

                System.out.print("\nWant to update this record? (Y/N): ");
                char choice = sc.next().charAt(0);

                if (choice == 'y' || choice == 'Y'){

                    int stopper = 1;
                    do{
                        System.out.print("Enter column name to update: ");
                        String col_name = sc.next();
                        System.out.print("Enter updated value: ");
                        String new_value = sc.next();
                 
                        String updateQuery = "UPDATE customers SET " + col_name + " = ? " + "WHERE  customerNumber = ?";
                        PreparedStatement updatePs = con.prepareStatement(updateQuery);
                        updatePs.setString(1, new_value);     
                        updatePs.setInt(2, customerNo);

                        int x = updatePs.executeUpdate(); 

                        if(x == 1){
                            System.out.println("\nRecord updated\n");
                            System.out.print("Do you still want to keep updating the record "+ customerNo +" (Y/N): ");
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

    public static void deleteCustomer(){
        System.out.println("===========================");
        System.out.println("      DELETE CUSTOMER      ");
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

                String delete = "DELETE FROM customers WHERE " + col_name + "= ?";
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

    public static void customerViewOrders(){
        System.out.println("===========================");
        System.out.println("    VIEW CUSTOMER ORDERS   ");
        System.out.println("===========================\n");

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbsales","root","12345");


            System.out.print("Enter customer number to view: ");
            customerNo = sc.nextInt();
                        sc.nextLine();
           
            String view = "SELECT * FROM customers WHERE customerNumber = ?";
            PreparedStatement ps1 = con.prepareStatement(view);
            ps1.setInt(1, customerNo);
            ResultSet rs1 = ps1.executeQuery();

            if(rs1.next()){
                System.out.println("customerNumber: " + rs1.getInt("customerNumber"));
                System.out.println("customerName: " + rs1.getString("customerName"));
                System.out.println("contactLastName: " + rs1.getString("contactLastName"));
                System.out.println("contactFirstName: " + rs1.getString("contactFirstName"));
                System.out.println("phone: " + rs1.getString("phone"));
                System.out.println("addressLine1: " + rs1.getString("addressLine1"));
                System.out.println("addressLine2: " + rs1.getObject("addressLine2"));
                System.out.println("city: " +  rs1.getString("city"));
                System.out.println("state: " + rs1.getObject("state"));
                System.out.println("postalCode: " + rs1.getObject("postalCode"));
                System.out.println("country: " + rs1.getString("country"));
                System.out.println("salesRepEmployeeNumber: " + rs1.getObject("salesRepEmployeeNumber"));
                System.out.println("creditLimit: " + rs1.getDouble("creditLimit"));
            }

            System.out.println();
            System.out.print("Enter year to view: ");
            String year = sc.next();

            String viewYear =   "SELECT o.orderNumber, o.orderDate, o.shippedDate, o.status" + 
                                "\nFROM customers c  LEFT JOIN orders o on c.customerNumber = o.customerNumber "
                                + "\nWHERE c.customerNumber = " + customerNo
                                + "\nAND YEAR(orderDate) = " + year;

            PreparedStatement ps2 = con.prepareStatement(viewYear, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs2 = ps2.executeQuery();
            
            if(rs2.next()){
                rs2.beforeFirst();
                while (rs2.next()) {   
                    String orderNumber = rs2.getString("o.orderNumber");
                    String orderDate = rs2.getString("o.orderDate");
                    String shipDate = rs2.getString("o.shippedDate");
                    String status = rs2.getString("o.status");
                    
                    System.out.println();
                    System.out.println("Order Number: "+ orderNumber);
                    System.out.println("Order Date:" + orderDate);
                    System.out.println("Shipped Date: " + shipDate);
                    System.out.println("Status: " + status + "\n");
                }
            } else {
                System.out.println("No orders made during this year\n");
            }   
            ps1.close();
            ps2.close();
            con.close();
        } catch (Exception e){
            System.out.println(e);
        }
    }
}
