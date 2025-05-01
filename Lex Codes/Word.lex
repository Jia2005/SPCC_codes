%{ 
void display(char[],int); 
%} 
%% 
[a-zA-Z]+[\n] { 
int flag=1; 
display(yytext,flag); 
return 0; 
} 
[0-9]+[\n] { 
int flag=0; 
display(yytext,flag); 
return 0; 
} 
.+ { 
int flag=-1; 
display(yytext,flag); 
return 0; 
} 
%% 
void display(char string[],int flag){ 
if(flag==1) 
printf("\nThe string \"%s\" is a Word\n", string); 
else if(flag==0) 
printf("\nThe string \"%d\" is a digit\n", atoi(string)); 
else 
printf("\nThe string is neither a word nor a digit\n"); 
} 
int main(){ 
printf("\nEnter a string to check:\n"); 
yylex(); 
return 0; 
}
