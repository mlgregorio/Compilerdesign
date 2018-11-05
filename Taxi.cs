using System;
namespace Taxi
{
  public class PassengerFare
  {
    public double km;
    public double minute;
  }

  public class TaxiMeter
  {
    public static void Main(string[] args)
    {
      double minFare = 35.0;
      double income = 0.0;
      string value = "";
      PassengerFare newPassenger = new PassengerFare();
      do {
        Start:
          Console.WriteLine("Do you have a passenger?: Y or N");
  		  value = Console.ReadLine();

        if (value != "y" || value != "Y") {
          goto Finish;
        }

        Console.WriteLine("Please enter how many kilometers traveled: ");
        newPassenger.km = double.Parse(Console.ReadLine());

        Console.WriteLine("Please enter the time spent (minutes): ");
        newPassenger.minute = double.Parse(Console.ReadLine());

        double costPerKm = newPassenger.km * 13 ;
        double costPerMin = newPassenger.minute * 2;
        double totalFare = minFare + costPerKm + costPerMin;
        Console.WriteLine("The Total Fare of the passenger is " + totalFare + ".");
        income = income + totalFare;

        Console.WriteLine("Do you want to compute your total Fare for the day or new passenger?: Y or N");
        value = Console.ReadLine();

        if (value == "y" || value == "Y") {
          Console.WriteLine("Your total income for the day is "+ income + ".");
        } else {
          goto Start;
        }
      } while (value == "y" || value == "Y");
      Finish:
        Console.WriteLine("Thank you!");
    }

  }

}
