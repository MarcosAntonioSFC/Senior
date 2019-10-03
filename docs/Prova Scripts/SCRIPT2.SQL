DECLARE
/*----- EXAME_NF -----*/
    idnf         NUMBER        := 0;
numero       NUMBER        := 0;
datacadastro date;
totalgeral   NUMBER        := 0;

/*----- EXAME_ITEMNF -----*/
iditemnf     NUMBER        := 0;
idproduto    NUMBER        := 0;
qtde         NUMBER(10, 4) := 0;
Valor        Number(10, 2) := 0;

/*----- Variaveis -------*/
Contador     NUMBER        := 0;

BEGIN
Select SYSDATE - 10
into datacadastro
From Dual;

FOR I IN 1..1000
        LOOP
            Contador := Contador + 1;
IF Contador > 100 Then
                Contador := 1;
datacadastro := datacadastro + 1;
END IF;

IDNF := IDNF + 1;
NUMERO := NUMERO + 1;

INSERT INTO EXAME_NF (IDNF, NUMERO, DATACADASTRO, TOTALGERAL)
VALUES (idnf, numero, datacadastro, 20.00);

INSERT INTO EXAME_ITEMNF (IDITEMNF, IDNF, IDPRODUTO, QTDE, VALOR)
VALUES (1, idnf, 12, 1.0000, 5.00);
INSERT INTO EXAME_ITEMNF (IDITEMNF, IDNF, IDPRODUTO, QTDE, VALOR)
VALUES (2, idnf, 13, 1.0000, 5.00);
INSERT INTO EXAME_ITEMNF (IDITEMNF, IDNF, IDPRODUTO, QTDE, VALOR)
VALUES (3, idnf, 435, 1.0000, 10.00);

END LOOP;
COMMIT;
END;