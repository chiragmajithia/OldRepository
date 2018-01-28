import java.io.*;
import java.sql.*;


    public class ExcelReadTest
    {


        	public static void main(String[] args)
                {
        		Connection connection = null;
                        double x[]=new double[2500];
                        double y[]=new double[2500];
                        int i=1;
            		try{
            			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            			Connection con = DriverManager.getConnection( "jdbc:odbc:test" );
            			Statement st = con.createStatement();
            			ResultSet rs = st.executeQuery( "Select * from [F0000CH1$]" );

            			ResultSetMetaData rsmd = rs.getMetaData();
            			int numberOfColumns = rsmd.getColumnCount();
                                System.out.println("Number of Columns= "+numberOfColumns);


                                        
                			while( rs.next())
                                        {
                                             x[i]=Double.parseDouble(rs.getString(4));
                                             y[i]=Double.parseDouble(rs.getString(5));
                                             i++;
                                        }
                                        

                    			st.close();
                    			con.close();
                          }
                          catch(Exception ex)
                          {
                        	System.err.print("Exception: ");
                        	System.err.println(ex.getMessage());
                          }
                        for(i=0;i<2500;i++)
                        {
                            System.out.println("x["+i+"]= "+x[i]+"y["+i+"]= "+y[i]);
                        }
                }

    }
