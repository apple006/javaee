/*
plsql����������
*/
set serveroutput on;
declare 
  cursor cemp  is select ename from emp where deptno=50;
  pename emp.ename%type;
  
  --����һ��exception
  no_emp_found exception;
begin
  open cemp;
  
  --��ȡ������Ϣ
  fetch cemp into pename;
  if cemp%notfound then
    --�׳���Ӧ��exception
    raise no_emp_found;
  end if;
  
  
  close cemp;

exception
  when no_emp_found then dbms_output.put_line('���ҵ�Ա�����ܴ���');
  when others then dbms_output.put_line('�����쳣');
end;
/