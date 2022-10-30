with Ada.Text_IO, Ada.Integer_Text_IO;

procedure Hello is
begin
  Ada.Text_IO.Put_Line ("Hello Ada World!");

  for I in 1..5 loop
    Ada.Integer_Text_IO.Put(I);
  end loop;
end Hello;
