with Ada.Text_IO; use Ada.Text_IO;
with Ada.Integer_Text_IO; use Ada.Integer_Text_IO;

with Ada.Calendar; use Ada.Calendar;

procedure Hello is

  package Fix_IO is new Ada.Text_IO.Fixed_IO(DAY_DURATION);
  use Fix_IO;

  Year,Month,Day : INTEGER;
  Start,Seconds  : DAY_DURATION;
  Time_And_Date  : TIME;

begin

  Put_Line("Begin 3.14 second delay");
  delay 3.14;
  Put_Line("End of 3.14 second delay");

  Time_And_Date := Clock;
  Split(Time_And_Date, Year, Month, Day, Start);  -- get start time

  for Index in 1..9 loop
    Put("The date and time are now");
    Time_And_Date := Clock;
    Split(Time_And_Date, Year, Month, Day, Seconds);
    Put(Month, 3);
    delay 0.2;
    Put(Day, 3);
    delay 0.1;
    Put(Year, 5);
    delay 0.1;
    Put(Seconds - Start, 8, 3, 0);
    New_Line;
    delay 0.6;
  end loop;

  Put_Line("Begin non-accumulative timing loop here.");

  Time_And_Date := Clock;
  Split(Time_And_Date, Year, Month, Day, Start);  -- get start time
  for Index in 1..9 loop
    Time_And_Date := Clock;
    Split(Time_And_Date, Year, Month, Day, Seconds);
    Put("The elapsed time is");
    Put(Seconds - Start, 8, 3, 0);
    New_Line;
    delay DAY_DURATION(Index) - (Seconds - Start);
  end loop;

  Time_And_Date := Clock;
  for Index in 1..12 loop
    Time_And_Date := Time_And_Date + 0.4;
    delay until Time_And_Date;
    Put("Tick ");
  end loop;
  New_Line;

end Hello;