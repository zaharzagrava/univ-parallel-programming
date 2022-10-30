with Ada.Text_IO, Ada.Integer_Text_IO;
use Ada.Text_IO, Ada.Integer_Text_IO;

procedure Hello is
  task My_Task;

  task body My_Task is
  begin
    for I in 1..5 loop
      Ada.Integer_Text_IO.Put(I);
    end loop;
  end My_Task;
begin
  for I in 1..5 loop
    Ada.Integer_Text_IO.Put(I);
  end loop;
end Hello;