
INSERT INTO DEMO.CFG_VALUE (ID,PADRE,CLAVE,VALOR,VALOR_ALTERNO,FECHA_CREACION,ESTADO) 
VALUES (DEMO.SEQ_CFG_VALUE.NEXTVAL,NULL,'JWT_PARAMETER','Parametros para seguridad de jwt', null, sysdate, '1');

INSERT INTO DEMO.CFG_VALUE (ID, PADRE, CLAVE, VALOR, VALOR_ALTERNO, FECHA_CREACION, ESTADO) 
VALUES 
(DEMO.SEQ_CFG_VALUE.NEXTVAL,(SELECT ID FROM DEMO.CFG_VALUE WHERE PADRE IS NULL AND CLAVE = 'JWT_PARAMETER'), 'AUTHORITIES', 'Authorities', 'Clave de autorizaciones dentro del token', SYSDATE,'1');

INSERT INTO DEMO.CFG_VALUE (ID, PADRE, CLAVE, VALOR, VALOR_ALTERNO, FECHA_CREACION, ESTADO) 
VALUES 
(DEMO.SEQ_CFG_VALUE.NEXTVAL,(SELECT ID FROM DEMO.CFG_VALUE WHERE PADRE IS NULL AND CLAVE = 'JWT_PARAMETER'), 'PREFIXTOKEN', 'Bearer', 'Prefijo del token', SYSDATE,'1');

INSERT INTO DEMO.CFG_VALUE (ID, PADRE, CLAVE, VALOR, VALOR_ALTERNO, FECHA_CREACION, ESTADO) 
VALUES 
(DEMO.SEQ_CFG_VALUE.NEXTVAL,(SELECT ID FROM DEMO.CFG_VALUE WHERE PADRE IS NULL AND CLAVE = 'JWT_PARAMETER'), 'HEADER', 'Authorization', 'Nombre de la cabecera para la autorizacion', SYSDATE,'1');
 
INSERT INTO DEMO.CFG_VALUE (ID, PADRE, CLAVE, VALOR, VALOR_ALTERNO, FECHA_CREACION, ESTADO) 
VALUES 
(DEMO.SEQ_CFG_VALUE.NEXTVAL,(SELECT ID FROM DEMO.CFG_VALUE WHERE PADRE IS NULL AND CLAVE = 'JWT_PARAMETER'), 'SECRET', 'mySecretKey', 'Llave secreta para jwt', SYSDATE,'1');
