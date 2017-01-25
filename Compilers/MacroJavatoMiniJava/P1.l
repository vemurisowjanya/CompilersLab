	//FLEX FILE P1.l 


%{
#include <stdio.h>
#include<stdlib.h>	
#include <string.h>	
#include "P1.tab.h" // the output of bison on P1.y
void yyerror(char*); 
int yyparse(void); 
%}

%%
"("					{yylval.char_val = (char *)malloc(strlen(yytext)+1);
					strcpy(yylval.char_val,yytext);
					return LEFTPAREN;}
")"					{yylval.char_val = (char *)malloc(strlen(yytext)+1);
					strcpy(yylval.char_val,yytext);
					return RIGHTPAREN;}
"{"					{yylval.char_val = (char *)malloc(strlen(yytext)+1);
					strcpy(yylval.char_val,yytext);
					return LEFTBRACE;}
"}"					{yylval.char_val = (char *)malloc(strlen(yytext)+1);
					strcpy(yylval.char_val,yytext);
					return RIGHTBRACE;}
"["					{yylval.char_val = (char *)malloc(strlen(yytext)+1);
					strcpy(yylval.char_val,yytext);
					return LEFTBR;}
"]"					{yylval.char_val = (char *)malloc(strlen(yytext)+1);
					strcpy(yylval.char_val,yytext);
					return RIGHTBR;}					
"public"			{yylval.char_val = (char *)malloc(strlen(yytext)+1);
					strcpy(yylval.char_val,yytext);
					return PUBLIC;}
"static"			{yylval.char_val = (char *)malloc(strlen(yytext)+1);
					strcpy(yylval.char_val,yytext);
					return STATIC;}
"main"				{yylval.char_val = (char *)malloc(strlen(yytext)+1);
					strcpy(yylval.char_val,yytext);
					return MAIN;}
"void"				{yylval.char_val = (char *)malloc(strlen(yytext)+1);
					strcpy(yylval.char_val,yytext);
					return VOID;}
"String"			{yylval.char_val = (char *)malloc(strlen(yytext)+1);
					strcpy(yylval.char_val,yytext);
					return STRING;}
"class"				{yylval.char_val = (char *)malloc(strlen(yytext)+1);
					strcpy(yylval.char_val,yytext);
					return CLASS;}
"extends"			{yylval.char_val = (char *)malloc(strlen(yytext)+1);
					strcpy(yylval.char_val,yytext);
					return EXTENDS;}
"return"			{yylval.char_val = (char *)malloc(strlen(yytext)+1);
					strcpy(yylval.char_val,yytext);
					return RETURN;}
"int"				{yylval.char_val = (char *)malloc(strlen(yytext)+1);
						strcpy(yylval.char_val,yytext);
					return INT;}
"boolean"			{yylval.char_val = (char *)malloc(strlen(yytext)+1);
					strcpy(yylval.char_val,yytext);
					return BOOLEAN;}
","					{yylval.char_val = (char *)malloc(strlen(yytext)+1);
					strcpy(yylval.char_val,yytext);
					return COMMA;}
"if"				{yylval.char_val = (char *)malloc(strlen(yytext)+1);
					strcpy(yylval.char_val,yytext);
					return IF_;}
"else"				{yylval.char_val = (char *)malloc(strlen(yytext)+1);
					strcpy(yylval.char_val,yytext);
					return ELSE_;}
"="					{yylval.char_val = (char *)malloc(strlen(yytext)+1);
					strcpy(yylval.char_val,yytext);
					return ASSIGN;}
"length"			{yylval.char_val = (char *)malloc(strlen(yytext)+1);
					strcpy(yylval.char_val,yytext);
					return LENGTH;}
"System.out.println"	{yylval.char_val = (char *)malloc(strlen(yytext)+1);
						strcpy(yylval.char_val,yytext);
						return PRINTLN;}
"+"					{yylval.char_val = (char *)malloc(strlen(yytext)+1);
					strcpy(yylval.char_val,yytext);;
					return PLUS;}
"-"					{yylval.char_val = (char *)malloc(strlen(yytext)+1);
					strcpy(yylval.char_val,yytext);
					return MINUS;}
"*"					{yylval.char_val = (char *)malloc(strlen(yytext)+1);
					strcpy(yylval.char_val,yytext);
					return MULTIPLY;}
"/"					{yylval.char_val = (char *)malloc(strlen(yytext)+1);
					strcpy(yylval.char_val,yytext);
					return DIVIDE;}
"||"				{yylval.char_val = (char *)malloc(strlen(yytext)+1);
					strcpy(yylval.char_val,yytext);
					return BIT_OR;}
"&&"				{yylval.char_val = (char *)malloc(strlen(yytext)+1);
					strcpy(yylval.char_val,yytext);
					return BIT_AND;}
"!="				{yylval.char_val = (char *)malloc(strlen(yytext)+1);
					strcpy(yylval.char_val,yytext);
					return NOTEQ;}
"<="				{yylval.char_val = (char *)malloc(strlen(yytext)+1);
					strcpy(yylval.char_val,yytext);
					return LESSEQ;}
"true"				{yylval.char_val = (char *)malloc(strlen(yytext)+1);
					strcpy(yylval.char_val,yytext);
					return TRUEVAL;}
"false"				{yylval.char_val = (char *)malloc(strlen(yytext)+1);
					strcpy(yylval.char_val,yytext);
					return FALSEVAL;}
"this"				{yylval.char_val = (char *)malloc(strlen(yytext)+1);
					strcpy(yylval.char_val,yytext);
					return THIS;}
"new"				{yylval.char_val = (char *)malloc(strlen(yytext)+1);
					strcpy(yylval.char_val,yytext);
					return NEW;}
"!"					{yylval.char_val = (char *)malloc(strlen(yytext)+1);
					strcpy(yylval.char_val,yytext);
					return NOT;}
"#"					{yylval.char_val = (char *)malloc(strlen(yytext)+1);
					strcpy(yylval.char_val,yytext);
					return HASH;}
"define"			{yylval.char_val = (char *)malloc(strlen(yytext)+1);
					strcpy(yylval.char_val,yytext);
					return DEFINE;}
"while"				{yylval.char_val = (char *)malloc(strlen(yytext)+1);
					strcpy(yylval.char_val,yytext);
					return WHILE;}
"."             	{yylval.char_val = (char *)malloc(strlen(yytext)+1);
					strcpy(yylval.char_val,yytext);
					return PERIOD;}
";"					{yylval.char_val = (char *)malloc(strlen(yytext)+1);
					strcpy(yylval.char_val,yytext);
					return SEMICOLON;}
([1-9][0-9]+)|([0-9])			{ 
						//printf("Saw an integer %s\n",yytext); 
						yylval.char_val = (char *)malloc(strlen(yytext)+1);
						strcpy(yylval.char_val,yytext);
						return INTEGER_LITERAL; 
					}
([A-Z|a-z|_])([A-Z|a-z|_|0-9])*		{
						yylval.char_val = (char *)malloc(strlen(yytext)+1);
						strcpy(yylval.char_val,yytext);		
						return IDENTIFIER;
					}
[/][*][^*]*[*]+([^*/][^*]*[*]+)*[/] {}		


[/][/]([^\n\r])*[\n|\r|\r\n]			{}

[\000-\037]+ 		{} 		//do nothing on non-ascii characters

[ \t\n\r\f]+		{}		//do nothing on whitespace
<<EOF>>			{ return  0; }
.               { printf("Error while scanning tokens\n"); return 1;}


%%

void yyerror(char* str) { printf("// Failed to parse macrojava code.\n"); }

int yywrap(void) { return 1; }

int main(void) {
	yyparse();	
}
