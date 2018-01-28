public class Calculate
{
    static double cal(String s)
    {
        String substring;
        s=s+"=";
        int init=0, finl=0,flag=0,fsflag=0,negflag=0;
        double ans=0,no;
        int opindex=0;
        for(int i=0;i<s.length();i++)
        {
             if(s.charAt(0)=='-' && negflag==0)
             {
                    negflag=1;
                    i=1;
             }
            if((s.charAt(i)<'0' || s.charAt(i)>'9')&& s.charAt(i)!='.' && s.charAt(i)!='p' && s.charAt(i)!='i'  )
            {
                finl=i;
                substring=s.substring(init, finl);
                flag=0;
                if(substring.equalsIgnoreCase("pi"))
                no=3.1415926535897932384626433832795;
                else
                no=Double.valueOf(substring);
                System.out.println("Substring"+no);
                System.out.println("init"+init+"final"+finl);
                System.out.println("opindex"+opindex+s.charAt(opindex));
                if(fsflag==0)
                {
                    if(negflag==1)
                    ans=no*-1;
                    else
                    ans=no;
                    fsflag=1;
                }
                else
                switch(s.charAt(opindex))
                {
                case '+':
                        ans=ans+no;
                        break;
                case '-':
                       ans=ans-no;
                       break;
                case '*':
                       ans=ans*no;
                       break;
                case '/':
                       ans=ans/no;
                       break;
                }
                opindex=i;

            }
            else
            {
              if(flag==0)
              {
                  init=i;
                  flag=1;
              }
            }
            
        }
        return ans;
    }

    public static void main(String[] args)
    {
        System.out.println("no:="+cal("-2*pi"));
    }
}
