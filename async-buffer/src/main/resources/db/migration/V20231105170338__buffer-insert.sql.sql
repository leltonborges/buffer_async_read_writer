
INSERT INTO buffer.TB_TYPE_DOCUMENT (SEQ_TYPE_DOCUMENT, CD_TYPE, DS_DESCRIPTION)
VALUES (DEFAULT, 1, 'CPF'),
       (DEFAULT, 2, 'CNPJ'),
       (DEFAULT, 3, 'RG'),
       (DEFAULT, 4, 'CRM')
;

INSERT INTO buffer.TB_PERSON (SEQ_PERSON, VL_NAME, VL_DOCUMENT, CD_TYPE_DOCUMENT, CD_AGE)
VALUES (DEFAULT, 'Fulano de tal', '9464646', 3, 51),
       (DEFAULT, 'Ciclano de tal', '23523', 1, 51),
       (DEFAULT, 'Beutrano de tal', '6234565', 2, 51),
       (DEFAULT, 'Armelho de tal', '652345345', 4, 51)
;

