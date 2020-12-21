  CREATE TABLE "DEMO"."CFG_VALUE" 
   (	
    "ID" NUMBER(6,0), 
	"PADRE" NUMBER, 
	"CLAVE" VARCHAR2(30 BYTE), 
	"VALOR" VARCHAR2(50 BYTE), 
	"VALOR_ALTERNO" VARCHAR2(50 BYTE), 
	"FECHA_CREACION" TIMESTAMP (6), 
	"ESTADO" CHAR(1 BYTE) DEFAULT '1'
   ) ;

   COMMENT ON COLUMN "DEMO"."CFG_VALUE"."PADRE" IS 'Referencia recursiva al padre (tipo de configuración)';
   COMMENT ON TABLE "DEMO"."CFG_VALUE"  IS 'Tabla que almacena los valores de configuracion';

--------------------------------------------------------   
   --  DDL for Sequence SEQ_CFG_VALUE
--------------------------------------------------------

   CREATE SEQUENCE  "DEMO"."SEQ_CFG_VALUE"  MINVALUE 1 MAXVALUE 999999999999 INCREMENT BY 1 START WITH 1;