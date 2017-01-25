/* BISON FILE P1.y */

%{
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stdbool.h>
extern int yylex();
extern void yyerror(char *);
bool isMacro = false;
typedef struct lookup_macros{
	char* name;
	char* action;
	char* all_args;
	struct lookup_macros* next;
} macro_store;
macro_store *HeadAllMacros=NULL;
macro_store *TopAllMacros=NULL;
void store(char*,char*,char*);
char* find_replace(char* ,char* , char* ,int );
char* evaluate(char* ,char*);
int count_args(char* );
char* remove_delimiters(char* );
int find(char* );
%}

%union {
	int	int_val;
	char* char_val;
};

%token <char_val> SEMICOLON IDENTIFIER EOF_ LEFTPAREN RIGHTPAREN LEFTBR RIGHTBR LEFTBRACE RIGHTBRACE PUBLIC STATIC MAIN VOID STRING CLASS EXTENDS RETURN INT BOOLEAN COMMA IF_ ELSE_ ASSIGN PRINTLN BIT_AND BIT_OR NOTEQ LESSEQ PLUS MINUS MULTIPLY DIVIDE PERIOD TRUEVAL FALSEVAL THIS NEW NOT HASH DEFINE INTEGER_LITERAL WHILE LENGTH 

%type <char_val> Goal MacroDefinition_ MainClass TypeDeclaration_ TypeDeclaration Identifier Identifier_ Identifier_1 MacroDefinition MacroDefExpression MacroDefStatement typeIdentifier_1 MethodDeclaration_ MethodDeclaration typeIdentifier1 typeIdentifier2 typeIdentifier_2 typeIdentifier_3 Statement Statement_ Expression Expression_ PrimaryExpression Type Integer CheckMacro


%start Goal	

%%
Goal: MacroDefinition_ MainClass TypeDeclaration_ {
	$$ = (char*)malloc(strlen($2)+strlen($3)+1);
	strcpy($$,$2);
	strcat($$,"\n");
	strcat($$,$3);
	printf("%s ",$$);
}	

MacroDefinition_: MacroDefinition MacroDefinition_ 		{
	$$ = (char*)malloc(strlen($1)+strlen($2)+1);
	strcpy($$,$1);
	strcat($$,$2);
}
		| /*empty*/  {$$ = "";}

TypeDeclaration_: TypeDeclaration TypeDeclaration_ 	{
	$$ = (char*)malloc(strlen($1)+strlen($2)+1);
	strcpy($$,$1);
	strcat($$,$2);
}
					| /*empty*/  {$$ = "";}

MainClass: CLASS Identifier LEFTBRACE PUBLIC STATIC VOID MAIN LEFTPAREN STRING LEFTBR RIGHTBR Identifier RIGHTPAREN LEFTBRACE PRINTLN LEFTPAREN Expression RIGHTPAREN SEMICOLON RIGHTBRACE RIGHTBRACE 	{
				$$ = (char*)malloc(strlen($3)+strlen($5)+strlen($1)+strlen($2)+strlen($4)+strlen($6)+strlen($7)+strlen($8)+strlen($9)+strlen($10)+strlen($11)+strlen($12)+strlen($13)+strlen($14)+strlen($15)+strlen($16)+strlen($17)+strlen($18)+strlen($19)+strlen($20)+strlen($21)+7);
				strcpy($$,$1);
				strcat($$," ");
				strcat($$,$2);
				strcat($$,$3);
				strcat($$,$4);
				strcat($$," ");
				strcat($$,$5);
				strcat($$," ");
				strcat($$,$6);
				strcat($$," ");
				strcat($$,$7);
				strcat($$,$8);
				strcat($$,$9);
				strcat($$,$10);
				strcat($$,$11);
				strcat($$," ");
				strcat($$,$12);
				strcat($$,$13);
				strcat($$,$14);
				strcat($$,$15);
				strcat($$,$16);
				strcat($$,$17);
				strcat($$,$18);
				strcat($$,$19);	
				strcat($$,$20);
				strcat($$,$21);
				} 

MethodDeclaration: PUBLIC Type Identifier LEFTPAREN typeIdentifier_3 RIGHTPAREN LEFTBRACE typeIdentifier_1 Statement_ RETURN Expression SEMICOLON RIGHTBRACE {
		$$ = (char*)malloc(strlen($1)+strlen($2)+strlen($3)+strlen($4)+strlen($5)+strlen($6)+strlen($7)+strlen($8)+strlen($9)+strlen($10)+strlen($11)+strlen($12)+strlen($13)+4);
		strcpy($$,$1);
		strcat($$," ");
		strcat($$,$2);
		strcat($$," ");
		strcat($$,$3);
		strcat($$,$4);
		strcat($$,$5);
		strcat($$,$6);
		strcat($$,$7);
		strcat($$,$8);
		strcat($$,$9);
		strcat($$,$10);
		strcat($$," ");
		strcat($$,$11);
		strcat($$,$12);
		strcat($$,$13);		
		}
TypeDeclaration: CLASS Identifier LEFTBRACE typeIdentifier_1 MethodDeclaration_ RIGHTBRACE 			{
				$$ = (char*)malloc(strlen($1)+strlen($2)+strlen($3)+strlen($4)+strlen($5)+strlen($6)+3);
				strcpy($$,$1);
				strcat($$," ");
				strcat($$,$2);
				strcat($$,$3);
				strcat($$,$4);
				strcat($$,$5);
				strcat($$,$6);
}
				| CLASS Identifier EXTENDS Identifier LEFTBRACE typeIdentifier_1 MethodDeclaration_ RIGHTBRACE	 {
				$$ = (char*)malloc(strlen($1)+strlen($2)+strlen($3)+strlen($4)+strlen($5)+strlen($6)+strlen($7)+strlen($8)+4);
				strcpy($$,$1);
				strcat($$," ");
				strcat($$,$2);
				strcat($$," ");
				strcat($$,$3);
				strcat($$," ");
				strcat($$,$4);
				strcat($$,$5);
				strcat($$,$6);				
				strcat($$,$7);
				strcat($$,$8);
			}

MethodDeclaration_: MethodDeclaration MethodDeclaration_ {
	$$ = (char*)malloc(strlen($1)+strlen($2)+1);
	strcpy($$,$1);
	strcat($$,$2);
}
			| /*empty*/			{$$ = "";}

typeIdentifier1: Type Identifier SEMICOLON {
	$$ = (char*)malloc(strlen($1)+strlen($2)+strlen($3)+2);
	strcpy($$,$1);
	strcat($$," ");
	strcat($$,$2);
	strcat($$,$3);
}
typeIdentifier_1: typeIdentifier_1 typeIdentifier1  {
	$$ = (char*)malloc(strlen($1)+strlen($2)+1);
	strcpy($$,$1);
	strcat($$,$2);
}
			| /*empty*/ {$$ = "";}

typeIdentifier_2: typeIdentifier2 typeIdentifier_2 {
	$$ = (char*)malloc(strlen($1)+strlen($2)+1);
	strcpy($$,$1);
	strcat($$,$2);
}
		| /*empty*/			{$$ = "";}

typeIdentifier2: COMMA Type Identifier  {
	$$ = (char*)malloc(strlen($1)+strlen($2)+strlen($3)+3);
	strcpy($$,$1);
	strcat($$,$2);
	strcat($$," ");
	strcat($$,$3);
}

typeIdentifier_3: Type Identifier typeIdentifier_2 {
	$$ = (char*)malloc(strlen($1)+strlen($2)+strlen($3)+2); 
	strcpy($$,$1);
	strcat($$," ");
	strcat($$,$2);
	strcat($$,$3);
	}
			| /*empty*/			{$$ = "";} 
Type: INT LEFTBR RIGHTBR {
	$$ = (char*)malloc(strlen($1)+strlen($2)+1+strlen($3));
	strcpy($$,$1);
	strcat($$,$2);
	strcat($$,$3);
}
		| BOOLEAN	{
								$$ = (char*)malloc(strlen($1)+1);
								strcpy($$,$1);
					}	
		 | INT 		{
								$$ = (char*)malloc(strlen($1)+1);
								strcpy($$,$1);
					}	
		  | Identifier   {
								$$ = (char*)malloc(strlen($1)+1);
								strcpy($$,$1);
						}	

Statement: LEFTBRACE Statement_ RIGHTBRACE    {
	$$ = (char*)malloc(strlen($1)+strlen($2)+strlen($3)+1);
	strcpy($$,$1);
	strcat($$,$2);
	strcat($$,$3);
}
			| PRINTLN LEFTPAREN Expression RIGHTPAREN SEMICOLON 	{
				$$ = (char*)malloc(strlen($1)+strlen($2)+strlen($3)+strlen($4)+strlen($5)+2);
				strcpy($$,$1);
				strcat($$,$2);
				strcat($$,$3);
				strcat($$,$4);
				strcat($$,$5);
			}
			| Identifier ASSIGN Expression SEMICOLON				{
				$$ = (char*)malloc(strlen($1)+strlen($3)+strlen($2)+strlen($4)+4);
				strcpy($$,$1);
				strcat($$,$2);
				strcat($$,$3);
				strcat($$,$4);
			}
			| Identifier LEFTBR Expression RIGHTBR ASSIGN Expression SEMICOLON     {
				$$ = (char*)malloc(strlen($1)+strlen($2)+strlen($3)+strlen($4)+strlen($5)+strlen($6)+strlen($7)+4);
				strcpy($$,$1);
				strcat($$,$2);
				strcat($$,$3);
				strcat($$,$4);
				strcat($$,$5);
				strcat($$,$6);
				strcat($$,$7);
			}
			| IF_ LEFTPAREN Expression RIGHTPAREN Statement     {
				$$ = (char*)malloc(strlen($3)+strlen($5)+strlen($1)+strlen($2)+strlen($4)+3);
				strcpy($$,$1);
				strcat($$,$2);
				strcat($$,$3);
				strcat($$,$4);
				strcat($$,$5);
			}
			| IF_ LEFTPAREN Expression RIGHTPAREN Statement ELSE_ Statement  {
				$$ = (char*)malloc(strlen($3)+strlen($5)+strlen($1)+strlen($2)+strlen($4)+strlen($6)+strlen($7)+4);
				strcpy($$,$1);
				strcat($$,$2);
				strcat($$,$3);
				strcat($$,$4);
				strcat($$,$5);
				strcat($$,$6);
				strcat($$," ");
				strcat($$,$7);
			}  
			| WHILE LEFTPAREN Expression RIGHTPAREN Statement     {
				$$ = (char*)malloc(strlen($3)+strlen($5)+strlen($1)+strlen($2)+strlen($4)+2);
				strcpy($$,$1);
				strcat($$,$2);
				strcat($$,$3);
				strcat($$,$4);
				strcat($$,$5);
			}
			| Identifier LEFTPAREN Expression Expression_ RIGHTPAREN SEMICOLON   {
					char *temp = malloc(strlen($3)+strlen($4)+1);
					strcpy(temp,$3);
					strcat(temp,$4);
					char* name = (char*)malloc(strlen($1)+3);
					strcpy(name,"%");
					strcat(name,$1);
					strcat(name,"%");
					$$ = evaluate(name,temp);
					if($$==NULL){
					$$ = (char*)malloc(strlen($3)+strlen($5)+strlen($1)+strlen($2)+strlen($4)+strlen($6)+1);
					strcpy($$,$1);
					strcat($$,$2);
					strcat($$,$3);
					strcat($$,$4);
					strcat($$,$5);
					strcat($$,$6);
				}
			}   
			| Identifier LEFTPAREN RIGHTPAREN SEMICOLON			{
					char* name = (char*)malloc(strlen($1)+3);
					strcpy(name,"%");
					strcat(name,$1);
					strcat(name,"%");				
					$$ = evaluate(name,NULL);
					if($$ == NULL){
					$$ = (char*)malloc(strlen($1)+strlen($2)+strlen($3)+strlen($4)+1);
					strcpy($$,$1);
					strcat($$,$2);
					strcat($$,$3);
					strcat($$,$4);
				}
			}

Expression_: COMMA Expression Expression_ 	{
	$$ = (char*)malloc(strlen($1)+strlen($2)+strlen($3)+2);
	strcpy($$,$1);
	strcat($$,$2);
	strcat($$,$3);
}
		 | /*empty*/			{$$ = "";}

Expression: PrimaryExpression BIT_AND PrimaryExpression   {
	$$ = (char*)malloc(strlen($1)+strlen($3)+strlen($2)+3);
	strcpy($$,$1);
	strcat($$,$2);
	strcat($$,$3);
}
			| PrimaryExpression BIT_OR PrimaryExpression    {
	$$ = (char*)malloc(strlen($1)+strlen($3)+3+strlen($2));
	strcpy($$,$1);
	strcat($$,$2);
	strcat($$,$3);	
}
			| PrimaryExpression NOTEQ PrimaryExpression  	 {
	$$ = (char*)malloc(strlen($1)+strlen($3)+3+strlen($2));
	strcpy($$,$1);
	strcat($$,$2);
	strcat($$,$3);
}  
			| PrimaryExpression LESSEQ PrimaryExpression	 {
	$$ = (char*)malloc(strlen($1)+strlen($2)+strlen($3)+3);
	strcpy($$,$1);
	strcat($$,$2);
	strcat($$,$3);
	
} 
			| PrimaryExpression PLUS PrimaryExpression    	 {
	$$ = (char*)malloc(strlen($1)+strlen($2)+strlen($3)+3);
	strcpy($$,$1);
	strcat($$,$2);
	strcat($$,$3);	
}
			| PrimaryExpression MINUS PrimaryExpression    		 {
	$$ = (char*)malloc(strlen($1)+strlen($2)+strlen($3)+3);
	strcpy($$,$1);
	strcat($$,$2);
	strcat($$,$3);
}
			| PrimaryExpression MULTIPLY PrimaryExpression  	 {
	$$ = (char*)malloc(strlen($1)+strlen($2)+strlen($3)+3);
	strcpy($$,$1);
	strcat($$,$2);
	strcat($$,$3);	
}
			| PrimaryExpression DIVIDE PrimaryExpression   		 {
	$$ = (char*)malloc(strlen($1)+strlen($2)+strlen($3)+3);
	strcpy($$,$1);
	strcat($$,$2);
	strcat($$,$3);	
}
			| PrimaryExpression LEFTBR PrimaryExpression RIGHTBR 	 {
	$$ = (char*)malloc(strlen($1)+strlen($3)+strlen($2)+strlen($4)+1);
	strcpy($$,$1);
	strcat($$,$2);
	strcat($$,$3);
	strcat($$,$4);
}
			| PrimaryExpression PERIOD LENGTH   	{
	$$ = (char*)malloc(strlen($1)+strlen($2)+strlen($3)+1);
	strcpy($$,$1);
	strcat($$,$2);
	strcat($$,$3);	
}
			| PrimaryExpression  			{
	$$ = (char*)malloc(strlen($1)+1);
	strcpy($$,$1);
}	
			| PrimaryExpression PERIOD Identifier LEFTPAREN Expression Expression_ RIGHTPAREN		 {
					$$ = (char*)malloc(strlen($1)+strlen($3)+strlen($2)+strlen($4)+strlen($5)+strlen($6)+strlen($7)+1);
					strcpy($$,$1);
					strcat($$,$2);
					strcat($$,$3);
					strcat($$,$4);
					strcat($$,$5);
					strcat($$,$6);
					strcat($$,$7);	
}
			| PrimaryExpression PERIOD Identifier LEFTPAREN RIGHTPAREN		{
					$$ = (char*)malloc(strlen($1)+strlen($3)+strlen($2)+strlen($4)+strlen($5)+1);
					strcpy($$,$1);
					strcat($$,$2);
					strcat($$,$3);
					strcat($$,$4);
					strcat($$,$5);
}						  
			| Identifier LEFTPAREN Expression Expression_ RIGHTPAREN  {
					char *args = malloc(strlen($3)+strlen($4)+1);
					strcpy(args,$3);
					strcat(args,$4);
					char *name = malloc(strlen($1)+3);
					strcpy(name,"%");
					strcat(name,$1);
					strcat(name,"%");
					$$ = evaluate(name,args);
					if($$==NULL){
						$$ = (char*)malloc(strlen($1)+strlen($3)+strlen($2)+strlen($4)+strlen($5)+1);
						strcpy($$,$1);
						strcat($$,$2);
						strcat($$,$3);
						strcat($$,$4);
						strcat($$,$5);
				}
			}
			| Identifier LEFTPAREN RIGHTPAREN		{
					char *name = malloc(strlen($1)+3);
					strcpy(name,"%");
					strcat(name,$1);
					strcat(name,"%");
					$$ = evaluate(name,NULL);
					if($$==NULL){
						$$ = (char*)malloc(strlen($1)+strlen($3)+strlen($2)+1);
						strcpy($$,$1);
						strcat($$,$2);
						strcat($$,$3);
				}
			}

PrimaryExpression: Integer  {
								$$ = (char*)malloc(strlen($1)+1);
								strcpy($$,$1);
							}	
					| TRUEVAL	{	
									$$ = (char*)malloc(strlen($1)+1);
									strcpy($$,$1);
								}
					| FALSEVAL  {	
									$$ = (char*)malloc(strlen($1)+1);
									strcpy($$,$1);
								}
					| Identifier 	{
										$$ = (char*)malloc(strlen($1)+1);
										strcpy($$,$1);			
									} 
					| THIS   		{	
										$$ = (char*)malloc(strlen($1)+1);
										strcpy($$,$1);
									}
					| NEW INT LEFTBR Expression RIGHTBR  {
						$$ = (char*)malloc(strlen($1)+strlen($2)+strlen($3)+strlen($4)+strlen($5)+2);
						strcpy($$,$1);
						strcat($$," ");
						strcat($$,$2);
						strcat($$,$3);
						strcat($$,$4);
						strcat($$,$5);
				}
					| NEW Identifier LEFTPAREN RIGHTPAREN 	{
						$$ = (char*)malloc(strlen($1)+strlen($3)+strlen($2)+strlen($4)+2);
						strcpy($$,$1);
						strcat($$," ");
						strcat($$,$2);
						strcat($$,$3);
						strcat($$,$4);
				}
					| NOT Expression  						{
																$$ = (char*)malloc(strlen($1)+strlen($2)+1);
																strcpy($$,$1);
																strcat($$,$2);

															}
					| LEFTPAREN Expression RIGHTPAREN  		{
																$$ =(char*)malloc(strlen($1)+strlen($2)+strlen($3)+1);
																strcpy($$,$1);
																strcat($$,$2);
																strcat($$,$3);
															}

MacroDefinition: MacroDefExpression   			
					| MacroDefStatement  	

CheckMacro: HASH DEFINE 	{isMacro = true;}

MacroDefStatement: CheckMacro Identifier LEFTPAREN Identifier_1 RIGHTPAREN LEFTBRACE Statement_ RIGHTBRACE  {
	store($2,$4,$7);
} 

MacroDefExpression: CheckMacro Identifier LEFTPAREN Identifier_1 RIGHTPAREN LEFTPAREN Expression RIGHTPAREN	{
	char *temp = (char*)malloc(strlen($7)+3);
	strcpy(temp,"(");
	strcat(temp,$7);
	strcat(temp,")");
	store($2,$4,temp);
}

Identifier: IDENTIFIER 		{
	if(isMacro == true){
			$$ = (char*)malloc(strlen($1)+3);
			strcpy($$,"%");
			strcat($$,$1);
			strcat($$,"%");
		}
		else{
			$$ = (char*)malloc(strlen($1)+1);
			strcpy($$,$1);
		}
	}

Integer: INTEGER_LITERAL  			{
	$$ = (char*)malloc(strlen($1)+1);
	strcpy($$,$1);
}

Identifier_: COMMA Identifier Identifier_  {
	$$ = (char*)malloc(strlen($1)+strlen($2)+strlen($3)+2);
	strcpy($$,$1);
	strcat($$,$2);
	strcat($$,$3);}
		| /*empty*/			{$$ = "";}  

Identifier_1: Identifier Identifier_ 		{
	$$ = (char*)malloc(strlen($1)+strlen($2)+1);	
	strcpy($$,$1);
	strcat($$,$2);
	}
		| /*empty*/			{$$ = "";}

Statement_: Statement Statement_ 	{
	$$ = (char*)malloc(strlen($1)+strlen($2)+1);
	strcpy($$,$1);
	strcat($$,$2);
}
		| /*empty*/			{$$ = "";}

%%
void store(char* name, char* args, char *action){
	macro_store* Curr;
	if(TopAllMacros==NULL){
		TopAllMacros = (macro_store*)malloc(sizeof(macro_store));
		Curr = TopAllMacros;
	}
	else{
		HeadAllMacros->next = (macro_store*)malloc(sizeof(macro_store));
		Curr = HeadAllMacros->next;
	}
	Curr->name = (char*)malloc(strlen(name)+1);
	strcpy(Curr->name, name);
	Curr->all_args = (char*)malloc(strlen(args)+1);
	strcpy(Curr->all_args, args);
	Curr->next = NULL;
	Curr->action = (char*)malloc(strlen(action)+1);
	strcpy(Curr->action,action);
	HeadAllMacros = Curr;	
	isMacro = false;

}
char* evaluate(char* name, char* args){
	macro_store* Curr = TopAllMacros;
	if(args==NULL){
		while(Curr!=NULL){
			if(strcmp(Curr->name,name)==0){
				return Curr->action;
			}
			Curr = Curr->next;
		}
	}
	else{
		while(Curr!=NULL){
			int num_args;
			if(strcmp(Curr->name,name)==0){
				if((num_args = count_args(Curr->all_args))==count_args(args))
					return find_replace(Curr->all_args,args,Curr->action,num_args);
				else
				{
					printf("// Failed to parse macrojava code.\n");
					exit(0);
				}
		}
			Curr = Curr->next;
		}
	}
	return NULL;
}
char* find_replace(char* old_argstr, char* new_argstr, char* action,int num_args){
	char *old_args[num_args], *new_args[num_args];
	int i=0,j=0,k=0,count=0,offset=0;
	if(num_args==1)
	{
		old_args[0] = (char*)malloc(strlen(old_argstr)+1);
		strcpy(old_args[0],old_argstr);
		new_args[0] =  (char*)malloc(strlen(new_argstr)+1);
		strcpy(new_args[0],new_argstr);
	}
	else
	{
		while(i<strlen(old_argstr)+1){
			if(old_argstr[i]==',' || old_argstr[i]=='\0') 
			{
				old_args[count] = (char*)malloc(j+1);
				strncpy(old_args[count],old_argstr+offset,j);
				count++;
				j=0;
				offset = i+1;
			}
			else j++;
			i++;

		}
 		i=0,j=0,k=0,count=0,offset=0;
		while(i<strlen(new_argstr)+1){
			if(new_argstr[i]==',' || new_argstr[i]=='\0') 
			{
				new_args[count] = (char*)malloc(j+1);
				strncpy(new_args[count],new_argstr+offset,j);
				count++;
				j=0;
				offset = i+1;
			}
			else j++;
			i++;

		}
	}

	char *final = action;
	int tag=0;
	i=0,j=0,k=0,count=0;
	for(j=0;j<num_args;j++)
	{
		i=0;
		int size = strlen(action);
		while(i<size)
		{
			if(action[i]=='%')
			{
				if(tag==0)
				{
					tag=1;
					char *temp1 = (char*)malloc(strlen(old_args[j])+1);
					if(strlen(action+i)>=strlen(old_args[j]))
					{
						strncpy(temp1,action+i,strlen(old_args[j]));
							if(strcmp(temp1,old_args[j])==0)
							{
								char* str1 = final;
								final = (char*)malloc(strlen(final)+strlen(new_args[j])-strlen(old_args[j])+1);
								int n = find(str1);
								if(n!=-1)
								{
									strncpy(final,str1,n);
									strcat(final,new_args[j]);
									strcat(final,str1+n+strlen(old_args[j]));

								}
							}
					}
				}
				else tag=0;
			}
			i++;
		}
	}
	final = remove_delimiters(final);
	return final;
}

int find(char* s){
	int i=0;
	while(s[i]!='\0'){
		if(s[i]=='%')
			return i;
		i++;
	}
	return -1;
}
int count_args(char* s){
	int i=0;
	int count=1;
	while(s[i]!='\0'){
		if(s[i]==',') count++;
		i++;
	}
	return count;
}
char* remove_delimiters(char* s){
	int i=0;
	char* temp;
	if(strchr(s,'%')!=NULL){
		while(s[i]!='\0')
		{
			if(s[i]=='%'){
				temp = (char*)malloc(strlen(s));
				strncpy(temp,s,i);
				strcat(temp,s+i+1);
				s = temp;
			}
			i++;
		}
		return temp;
	}
	return s;
}