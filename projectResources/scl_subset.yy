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

program : FUNCTION IDENTIFIER left_paren right_paren statement_list ENDFUN
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

print_statement : PRINT left_paren arithmetic_exp right_paren
                ;

boolean_expression : arithmetic_exp relative_op arithmetic_exp
                   ;
relative_op : le_operator | lt_operator | ge_operator | gt_operator | eq_operator | ne_operator
            ;
arithmetic_exp : SET identifier | literal_integer | arithmetic_op arithmetic_exp arithmetic_exp
            ;
arithmetic_op : add_operator | sub_operator | mul_operator | div_operator
              ;