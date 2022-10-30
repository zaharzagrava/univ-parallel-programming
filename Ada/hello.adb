with Ada.Text_IO;
use Ada.Text_IO;

procedure Hello is
  task type My_Task (First : Character);

  task body My_Task is
  begin
    Put_Line("Start");
    for I in First .. 'Z' loop
      Put(I);
    end loop;
  end My_Task;

  Tab : array (0 .. 9) of My_Task ('G');
begin
  null;
end Hello;
