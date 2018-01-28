/* 
 * File:   main.cpp
 * Author: Chirag
 *
 * Created on May 29, 2011, 10:02 PM
 */
#include <cstdlib>
#include<iostream>
#include<stdio.h>
#include<math.h>
#include<fstream>
#include<conio.h>
using namespace std;
void init();
double integrate();
double integrate_sin(int);
double integrate_cos(int);
double simpson_sin(int);
double simpson_cos(int);
void calCoeff(int , int);
void answer();
float time[20000],fnc[20000],T,an[100],cn[100],bn[100];
unsigned int length=0;
/*
 * 
 */
void main()
{
    init();
    /*for(int i=0;i<=length;i++)
    {
        cout<<"i= "<<i<<"time = "<<time[i]<<" function ="<<fnc[i]<<"\n";
        if(i==100)
            getche();
    }*/
    //cout<<"S="<<simpson();
    getch();
    cout<<"integration="<<integrate();
    T=time[length-1]-time[0];
    cout<<"T="<<T;
    calCoeff(0,10);
    answer();
    return 0;
}

void init()
{
    ifstream file ( "square1.csv" ); // declare file stream: http://www.cplusplus.com/reference/iostream/ifstream/
    string value;
    while ( file.good() )
    {
        
         getline ( file, value, ',' ); // read a string until next comma: http://www.cplusplus.com/reference/string/getline/
         time[length]=atof(value.c_str());
         getline ( file, value, '\n' ); // read a string until next comma: http://www.cplusplus.com/reference/string/getline/
         fnc[length]=atof(value.c_str());
         length++;
    }
    length=length-1;
    
}

double integrate()
{
       double ans=0;
       float f1,f2,dx;
       int cnt=0;
        while (cnt<length-1)
        {
        //cout<<"cnt="<<cnt<<"\n";
	f1=fnc[cnt];
	f2=fnc[cnt+1];
        dx=time[cnt+1]-time[cnt];
        ans=(double)(ans+((f1+f2)/2*dx));
	cnt++;
        }
       // cout<<"trap="<<ans;
        return ans;
}

double integrate_sin(int n)
{
    double ans=0;
    float f1,f2,dx;
    int cnt=0;
    while (cnt<length-1)
    {
	f1=fnc[cnt]*sin((2*M_PI/T*n*time[cnt]));
	f2=fnc[cnt+1]*sin((2*M_PI/T*n*time[cnt]));
        dx=time[cnt+1]-time[cnt];
        ans=(double)(ans+((f1+f2)/2*dx));
	cnt++;
    }
   // cout<<"sin integration at n="<<n<<"is"<<ans<<'\n';
    return ans;
}

double integrate_cos(int n)
{
    double ans=0;
    float f1,f2,dx;
    int cnt=0;
    while (cnt<length-1)
    {
	f1=fnc[cnt]*cos((2*M_PI/T*n*time[cnt]));
	f2=fnc[cnt+1]*cos((2*M_PI/T*n*time[cnt]));
        dx=time[cnt+1]-time[cnt];
        ans=(double)(ans+((f1+f2)/2*dx));
	cnt++;
    }
    return ans;
}

void calCoeff(int f , int l)
{
    int i,c=0;
    double a0;
    a0=1/T*integrate();
    cout<<"\n c  \t\t an \t\t\t bn \t\t\t cn\n";
    for(i=f;i<=l;i++)
    {
        if(i==0)
        {
            an[c]=1/T*integrate();
            a0=an[c];
        }
        else
        an[c]=2/T*integrate_cos(i);
        bn[c]=2/T*integrate_sin(i);
        cn[c]=sqrt((an[c]*an[c])+(bn[c]*bn[c]))/a0*100;
        cout<<c<<"\t\t"<<an[c]<<"\t\t"<<bn[c]<<"\t\t"<<cn[c]<<"\n";
        getch();
        c++;
    }

}

void answer()
{
    char c;
    int f,l;
    while(1)
    {
        cout<<"contd(Y/N)";
        cin>>c;
        if(c=='N')
            break;
        else
        {
            cout<<"Enter the lower lmt of freq response ::";
            cin>>f;
            cout<<"Enter the upper lmt of freq response ::";
            cin>>l;
           // clrscr();
            calCoeff(f,l);
        }
    }
}

double simpson_sin(int n)
{
    double ans=0;
    float dx;
    int cnt=0;
    //cout<<"length:"<<length;
    getch();
    ans=fnc[cnt]*sin((2*M_PI/T*n*time[cnt]));
    cnt++;
       if(length%2==1)
       {
        while (cnt<length-1)
        {
       // cout<<"cnt="<<cnt<<"\n";
        //getch();
            if(cnt%2==1)
                {
                ans=ans+(4*fnc[cnt]*sin((2*M_PI/T*n*time[cnt])));
          //      cout<<"4 fnc["<<cnt<<"]";
                }
                else
                {
                ans=ans+(2*fnc[cnt]*sin((2*M_PI/T*n*time[cnt])));
            //    cout<<"2 fnc["<<cnt<<"]";
                }
             cnt++;
        }
       }
       ans+=fnc[cnt]*sin((2*M_PI/T*n*time[cnt]));
       //cout<<"time:"<<time[cnt];
       dx=time[1]-time[0];
       ans=dx/3*ans;
       //cout<<"simp="<<ans<<"dx="<<dx;
        return ans;
}
double simpson_cos(int n)
{
    double ans=0;
    float dx;
    int cnt=0;
    //cout<<"length:"<<length;
    getch();
    ans=fnc[cnt]*cos((2*M_PI/T*n*time[cnt]));
    cnt++;
       if(length%2==1)
       {
        while (cnt<length-1)
        {
       // cout<<"cnt="<<cnt<<"\n";
        //getch();
            if(cnt%2==1)
                {
                ans=ans+(4*fnc[cnt]*cos((2*M_PI/T*n*time[cnt])));
          //      cout<<"4 fnc["<<cnt<<"]";
                }
                else
                {
                ans=ans+(2*fnc[cnt]*cos((2*M_PI/T*n*time[cnt])));
            //    cout<<"2 fnc["<<cnt<<"]";
                }
             cnt++;
        }
       }
       ans+=fnc[cnt]*cos((2*M_PI/T*n*time[cnt]));
       //cout<<"time:"<<time[cnt];
       dx=time[1]-time[0];
       ans=dx/3*ans;
       //cout<<"simp="<<ans<<"dx="<<dx;
        return ans;
}
