USE Avaliacao_2_Lab_BD

GO

/*
    DROP FUNCTION fn_calcular_nota
*/

CREATE FUNCTION fn_calcular_nota(@n1 DECIMAL(4, 2), @n2 DECIMAL(4, 2), @n3 DECIMAL(4, 2))
RETURNS DECIMAL(4, 2)
BEGIN
    DECLARE @result DECIMAL(4, 2)

    IF (@n1 < 6.0 AND @n3 > @n1)
    BEGIN; SET @n1 = @n3; END
    IF (@n2 < 6.0 AND @n3 > @n2)
    BEGIN; SET @n2 = @n3; END

    SET @result = (@n1 + @n2)/2

    RETURN @result
END
