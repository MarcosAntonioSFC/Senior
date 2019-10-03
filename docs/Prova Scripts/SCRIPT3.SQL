CREATE
OR
REPLACE PROCEDURE DEFINIR_VALORES IS
    /*--- Declarando Variaveis---*/
    numero NUMBER (18, 2);
sum_valor        NUMBER(18, 2);

    /*--- Declarando o Cursor EXAME_NF ---*/
CURSOR Cur_EXAME_NF
        IS
SELECT IDNF
FROM EXAME_NF;
Reg_EXAME_NF     Cur_EXAME_NF%Rowtype;

    /*--- Declarando o Cursor EXAME_ITEMNF ---*/
CURSOR Cur_EXAME_ITEMNF
        IS
SELECT IDITEMNF, IDNF, IDPRODUTO, QTDE, VALOR
FROM EXAME_ITEMNF
WHERE IDNF = Reg_EXAME_NF.IDNF;
Reg_EXAME_ITEMNF Cur_EXAME_ITEMNF%Rowtype;

BEGIN OPEN Cur_EXAME_NF;
LOOP
        FETCH Cur_EXAME_NF INTO Reg_EXAME_NF;
EXIT
            WHEN Cur_EXAME_NF%NotFound;

sum_valor := 0;

OPEN Cur_EXAME_ITEMNF;
LOOP
            FETCH Cur_EXAME_ITEMNF INTO Reg_EXAME_ITEMNF;
EXIT
                WHEN Cur_EXAME_ITEMNF%NotFound;
            /*----------- Gera um numero aleatório -------------*/
dbms_random.initialize(to_number(TO_CHAR(systimestamp, 'HHmissFF4')));
numero := TRUNC(dbms_random.value(1, 100));
dbms_random.terminate;

            /*----------- Faz update na tabela com numero aleatório - alterando o valor do item ------*/
UPDATE EXAME_ITEMNF
SET VALOR = NUMERO
WHERE IDITEMNF = Reg_EXAME_ITEMNF.IDITEMNF
  AND IDNF = Reg_EXAME_ITEMNF.IDNF;

sum_valor := sum_valor + numero;
END LOOP;
CLOSE Cur_EXAME_ITEMNF;

UPDATE EXAME_NF
SET TOTALGERAL = sum_valor
WHERE IDNF = Reg_EXAME_NF.IDNF;

END LOOP;

CLOSE Cur_EXAME_NF;

COMMIT;
END;