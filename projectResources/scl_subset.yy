%{
/*
   SCL A Scientific Computing Language
//     for the low-level programming

//    First implemented as a pre-processor that generates C programs
//    Feb. 2016
//
//    Subset of SCL specified for YACC (Bison), Jan 2017
//    Department of Computer Science
//    Kennessaw State University

start : symbols forward_refs globals implement
      ;
symbols :
        | symbols symbol_def
        ;
symbol_def : SYMBOL IDENTIFIER HCON
           ;
forward_refs : 
             | FORWARD frefs
             ;
frefs  : REFERENCES forward_list
       | DECLARATIONS forward_list
       | forward_list
       ;
forward_list : forwards 
             | forward_list forwards
             ;
forwards : 
         | func_main dec_parameters
         ;

func_main : 
          | FUNCTION IDENTIFIER oper_type 
          | MAIN {dec_main();}  
          ;
oper_type : RETURN  chk_array ret_type 
          ;
chk_array : 
          | ARRAY array_dim_list
          ;
array_dim_list : LB array_index RB
               | array_dim_list LB array_index RB 
               ;
array_index : IDENTIFIER 
            | ICON 
            ;

ret_type  : TYPE type_name 
          | STRUCT IDENTIFIER 
          | STRUCTYPE IDENTIFIER 
          ;                     
type_name       : MVOID      
                | INTEGER   
                | SHORT    
                ;

globals : 
        | GLOBAL declarations 
        ;
declarations : 
             | DECLARATIONS const_dec var_dec
             ;
const_dec : CONSTANTS const_list
          ;
const_list : const_list DEFINE identifier rec_type equal_op constant_val
           ;
var_dec : VARIABLES var_list
        ;
var_list : var_list DEFINE identifier rec_type
         ;		
		  
implement : IMPLEMENTATIONS funct_list
          ;
funct_list : funct_def
           | funct_list funct_def
           ;
funct_def : funct_body
          ;
funct_body: FUNCTION main_head parameters f_body
          ;
main_head : MAIN 
          | IDENTIFIER
          ;       
parameters : 
           | PARAMETERS param_list
           ;
param_list : param_def
           | param_list COMMA param_def 
           ;
param_def : identifier chk_const chk_ptr chk_array TYPE type_name
          ;
chk_const :
          | CONSTANT 
          ;
f_body : declarations BEGIN <statement_list> ENDFUN
       ;
statement_list : statement 
               | statement_list statement
               ;
statement : if_statement
          | assignment_statement
		  | while_statement
		  | print_statement
		  | repeat_statement
          ;
if_statement : IF boolean_expression THEN statement_list 
      ELSE statement_list ENDIF
	         ;

while_statement : WHILE boolean_expression DO statement_list ENDWHILE
                ;

assignment_statement : SET identifier assignment_operator arithmetic_expression
                     ;

repeat_statement : REPEAT statement_list UNTIL boolean_expression ENDREPEAT
                 ;

print_statement : DISPLAY arg_list
                ;
arg_list : args
         | arg_list comma args
		 ;
args : identifier
     | constant
     | string
	 ;
	 
boolean_expression : arithmetic_exp relative_op arithmetic_exp
                   ;
relative_op : le_operator | lt_operator | ge_operator | gt_operator | eq_operator | ne_operator
            ;
arithmetic_exp : arithmetic_exp add_operator mulexp
               | arithmetic_exp sub_operator mulexp
			   | mulexp
			   ;
mulexp : mulexp mul_operator primary
       | mulexp div_operator primary
       | primary
       ;
primary : left_paren  arithmetic_exp right_paren
        | minus primary
		| constant_val
		| identifier
        ;		

