with Ada.Text_IO; use Ada.Text_IO;

procedure arrman is

  type my_array is array (1 .. 100) of Integer;

  a : my_array;

  function part_sum (left : Integer; Right : Integer) return Integer is
    sum : Integer := 0;
    i   : Integer;
  begin
    i := left;
    while i <= Right loop

      Put_Line( a(i)'Image );

      if a (i) rem 2 = 0 then
        sum := sum + a (i);
      end if;
      i := i + 1;
    end loop;
    return sum;
  end part_sum;

  procedure create_array is
  begin
    for i in a'Range loop
      a (i) := i;
      --Put (a (i)'img & " ");
    end loop;
    --New_Line;
  end create_array;

  task type my_task is
    entry start (left, RigHt : in Integer);
    entry finish (sum1 : out Integer);
  end my_task;

  task body my_task is
    left, RigHt : Integer;
    sum         : Integer := 0;

  begin
    Put_Line ("before start");
    accept start (left, RigHt : in Integer) do
      my_task.left  := left;
      my_task.RigHt := RigHt;
    end start;
    Put_Line ("after start");
    sum := part_sum (left, RigHt);
    accept finish (sum1 : out Integer) do
      sum1 := sum;
    end finish;
  end my_task;

  task1 : array (1 .. 3) of my_task;

  sum00 : Integer;
begin
  create_array;
  --  task1 (1).start (1, 3);
  Put_Line (part_sum (a'First, a'Last)'img);
  --  task1 (3).start (10, 30);

  --  task1 (1).start (a'First, a'Last);
  --  for i in task1'Range loop
  --    task1 (i).finish (sum00);
  --    Put_Line (sum00'Img & " ");
  --  end loop;

end arrman;
