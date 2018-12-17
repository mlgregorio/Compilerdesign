package edu.slu.cs311b;


import edu.slu.cs311b.Symbol;
import edu.slu.cs311b.Variable;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Scanner;

public class Interpreter {

    public static void interpret(Symbol root)   {
        Program p = new Program(root);
        p.interpret();
    }
}

// Rule No. 1: <program> -> <iden> <colon> <main> <start> <stmt_list> <end>
class Program {
    STMT_LIST stmt_list;

    public Program(Symbol lhs) {
        stmt_list = new STMT_LIST(lhs.children.get(4));
    }

    public void interpret() {
        stmt_list.interpret();
    }
}
// Rule No. 2: <stmt_list> -> <stmt> <stmt_list'>
class STMT_LIST {
    STMT stmt;
    STMT_LIST_PRIME stmt_list_prime;
    
    public STMT_LIST(Symbol lhs){
        stmt = STMT.construct(lhs.children.get(0));
        stmt_list_prime = STMT_LIST_PRIME.construct(lhs.children.get(1));
    }
    
    public void interpret() {
        stmt.interpret();
        stmt_list_prime.interpret();
    }
}

abstract class STMT_LIST_PRIME {
    public static STMT_LIST_PRIME construct(Symbol sym){
        switch (sym.ruleNo) {
            case 3:
                return new STMT_LIST_PRIME_1(sym);
                
            case 4:
                return new STMT_LIST_PRIME_2(sym);
            default:
                return null;
        }
    }
    public abstract void interpret();
}
// Rule No. 3:<stmt_list'> → <stmt> <stmt_list'>
class STMT_LIST_PRIME_1 extends STMT_LIST_PRIME {
    STMT stmt;
    STMT_LIST_PRIME stmt_list_prime;
    
    public STMT_LIST_PRIME_1(Symbol lhs){
        stmt = STMT.construct(lhs.children.get(0));
        stmt_list_prime = STMT_LIST_PRIME.construct(lhs.children.get(1));
    }
    
    @Override
    public void interpret(){
        stmt.interpret();
        stmt_list_prime.interpret();
    }
}
// Rule No. 4:<stmt_list'> → ε
class STMT_LIST_PRIME_2 extends STMT_LIST_PRIME {

    
    public STMT_LIST_PRIME_2(Symbol lhs){
        // rhs is EPSILON - do nothing
    }
    
    @Override
    public void interpret(){

    }
}
abstract class STMT {
    public static STMT construct(Symbol sym){
        switch (sym.ruleNo){
            case 5:
                return new STMT_1(sym);
            case 6:
                return new STMT_2(sym);
            case 7:
                return new STMT_3(sym);
            case 8:
                return new STMT_4(sym);
            case 9:
                return new STMT_5(sym);
            default:
                return null;
        }
    }
    public abstract void interpret();
}
// Rule No. 5:<stmt> → <declaration>
class STMT_1 extends STMT {

    DECLARATION declaration;

    public STMT_1(Symbol lhs) {
        declaration = new DECLARATION(lhs.children.get(0));
    }

    @Override
    public void interpret() {
        System.out.println("declaration.interpret()");
        declaration.interpret();
    }
}
// Rule No. 6:<stmt> → <assignment>
class STMT_2 extends STMT {

    ASSIGNMENT assignment;

    public STMT_2(Symbol lhs) {
        assignment =  new ASSIGNMENT(lhs.children.get(0));
    }

    @Override
    public void interpret() {
        System.out.println("assignment.interpret()");
        assignment.interpret();
    }
}
// Rule No. 7:<stmt> → <if_stmt>
class STMT_3 extends STMT {

    IF_STMT if_stmt;

    public STMT_3(Symbol lhs) {
        if_stmt = new IF_STMT(lhs.children.get(0));
    }

    @Override
    public void interpret() {
        System.out.println("if_stmt.interpret()");
        if_stmt.interpret();
    }
}
// Rule No. 8:<stmt> → <while_stmt>
class STMT_4 extends STMT {

    WHILE_STMT while_stmt;

    public STMT_4(Symbol lhs) {
        while_stmt = new WHILE_STMT(lhs.children.get(0));
    }

    @Override
    public void interpret() {
        System.out.println("while_stmt.interpret()");
        while_stmt.interpret();
    }
}

// Rule No. 9:<stmt> → <output>
class STMT_5 extends STMT {

    OUTPUT output;

    public STMT_5(Symbol lhs) {
        output = new OUTPUT(lhs.children.get(0));
    }

    @Override
    public void interpret() {
        System.out.println("output.interpret()");
        output.interpret();
    }
}

abstract class TERM {
    public static TERM construct(Symbol sym){
        switch (sym.ruleNo){
            case 10:
                return new TERM_1(sym);
            case 11:
                return new TERM_2(sym);
            default:
                return null;
        }
    }
    public abstract Object interpret();
}
// Rule No. 10:<term> → <iden>
class TERM_1 extends TERM {

    String iden;

    public TERM_1(Symbol lhs) {
        iden = lhs.children.get(0).lexeme;
    }

    @Override
    public Object interpret() {
        Variable var = Variable.symbolTable.get(iden);
        return var.value;
    }
    
}
// Rule No. 11:<term> → <value>
class TERM_2 extends TERM {

    VALUE value;

    public TERM_2(Symbol lhs) {
        value = VALUE.construct(lhs.children.get(0));
    }

    @Override
    public Object interpret() {
        return value.interpret();
    }
}

abstract class VALUE {
    public static VALUE construct(Symbol sym){
        switch (sym.ruleNo){
            case 12:
                return new VALUE_1(sym);
            case 13:
                return new VALUE_2(sym);
            default:
                return null;
        }
    }
    public abstract Object interpret();
}
// Rule No. 12:<value> → <str>
class VALUE_1 extends VALUE {

    String value;

    public VALUE_1(Symbol lhs) {
        value = lhs.children.get(0).lexeme;
    }

    @Override
    public Object interpret() {
        return value;
    }
}
// Rule No. 13:<value> → <number>
class VALUE_2 extends VALUE {

    String value;

    public VALUE_2(Symbol lhs) {
        value = lhs.children.get(0).lexeme;
    }

    @Override
    public Object interpret() {
        return Integer.parseInt(value);
    }
}

// Rule No. 14:<declaration> → <var> <iden> <colon> <type>
class DECLARATION {
    String iden;
    String type;
    
    public DECLARATION(Symbol lhs){
        iden = lhs.children.get(1).lexeme;
        type = lhs.children.get(3).lexeme;
    }
    
    public void interpret() {
       Variable var = new Variable(iden, type, -1);
    }
}


// Rule No. 15:<assignment> → <iden> <assignOp> <assignment_val>
class ASSIGNMENT {

    String iden;
    ASSIGNMENTVAL assignment_val;

    public ASSIGNMENT(Symbol lhs) {
        iden = lhs.children.get(0).lexeme;
        assignment_val = ASSIGNMENTVAL.construct(lhs.children.get(2));
    }

    public void interpret() {
        Variable var = Variable.symbolTable.get(iden);
        var.value = assignment_val.interpret();
    }
}

abstract class ASSIGNMENTVAL {
    public static ASSIGNMENTVAL construct(Symbol sym){
        switch (sym.ruleNo){
            case 16:
                return new ASSIGNMENTVAL_1(sym);
            case 17:
                return new ASSIGNMENTVAL_2(sym);
            default:
                return null;
        }
    }
    public abstract Object interpret();
}
// Rule No. 16:<assignment_val> → <expr>
class ASSIGNMENTVAL_1 extends ASSIGNMENTVAL{

    String iden;
    EXPR expr;

    public ASSIGNMENTVAL_1(Symbol lhs) {
        expr = new EXPR(lhs.children.get(0));
    }
    @Override
    public Object interpret() {
        return expr.interpret();
    }
}
// Rule No. 17:<assignment_val> → <input>
class ASSIGNMENTVAL_2 extends ASSIGNMENTVAL{

    INPUT input;

    public ASSIGNMENTVAL_2(Symbol lhs) {
        input = new INPUT(lhs.children.get(0));
    }
    @Override
    public Object interpret() {
        return input.interpret();
    }
}


// Rule No. 18:<expr> → <expr2> <expr'>
// Rule No. 19:<expr'> → <arithOp> <expr2> <expr'>
// Rule No. 20:<expr'> → ε
// Rule No. 21:<expr2> → <term> <expr2'>
// Rule No. 22:<expr2'> → <arithOp> <term> <expr2'>
// Rule No. 23:<expr2'> → ε

class EXPR {
    // DO NOT DELETE.
    // Use this class can evaluation arithmetic, relational or logical expressions
    // Just change the case labels/selectable segments in the switch statement to suit your programming langauge

    // See usage in LINE 192 and LINE 203 in MyInterpreter.java for your reference
       
    public LinkedList<Symbol> copyExpression(LinkedList<Symbol> expression){
        LinkedList<Symbol> copy = new LinkedList<>();
        for(Symbol s:expression){
        copy.add(s);
    }
        return copy;
    }
    
    LinkedList<Symbol> expression_main;
    Stack<Integer> operands = new Stack();

    public EXPR(Symbol lhs) {
        expression_main = Expression.getExpression(lhs);
    }

    public int interpret() {
        LinkedList<Symbol> expression = copyExpression(expression_main);
        Variable var;
        int result = 0;

        while (!expression.isEmpty()) {
            Symbol sym = expression.removeFirst();

            switch (sym.type) {
                // sym is an operand
                            
                case "<number>":
                    operands.push(Integer.parseInt(sym.lexeme));
                    break;

                case "<iden>":
                    var = Variable.symbolTable.get(sym.lexeme);
                    operands.push((Integer) var.value);
                    break;

                // sym is an operator
                default:
                    int operand2 = operands.pop();
                    int operand1 = operands.pop();

                    switch (sym.lexeme) {
                        case "+":
                            operands.push(operand1 + operand2);
                            break;

                        case "-":
                            operands.push(operand1 - operand2);
                            break;

                        case "*":
                            operands.push(operand1 * operand2);
                            break;

                        case "/":
                            operands.push(operand1 / operand2);
                            break;
                    }
            }
        }

        return operands.pop();
    }
}

// Rule No. 24:<if_stmt> → <if> <colon> <open_paren> <rel_expr> <close_paren> <then> <stmt_list'> <ifEnd>
class IF_STMT {
    STMT_LIST_PRIME stmt_list_prime;
    REL_EXPR rel_expr;
    
    public IF_STMT(Symbol lhs) {
        rel_expr = new REL_EXPR(lhs.children.get(3));
        stmt_list_prime = STMT_LIST_PRIME.construct(lhs.children.get(6));
    }

    public void interpret() {
        if (rel_expr.interpret()) {
            stmt_list_prime.interpret();
        }
    }
}
// Rule No. 25:<while_stmt> → <while> <colon> <open_paren> <rel_expr> <close_paren> <stmt_list'> <whileEnd>
class WHILE_STMT{
    STMT_LIST_PRIME stmt_list_prime;
    REL_EXPR rel_expr;
    
    public WHILE_STMT(Symbol lhs) {
        rel_expr = new REL_EXPR(lhs.children.get(3));
        stmt_list_prime = STMT_LIST_PRIME.construct(lhs.children.get(5));
    }

    public void interpret() {
        while(rel_expr.interpret()) {
            stmt_list_prime.interpret();
        }
    }
}

// Rule No. 26:<rel_expr> → <term> <relOp> <term>
class REL_EXPR  {
    TERM term1;
    String relOp;
    TERM term2;

    
    public REL_EXPR(Symbol lhs) {
        term1 = TERM.construct(lhs.children.get(0));
        relOp = lhs.children.get(1).lexeme;
        term2 = TERM.construct(lhs.children.get(2));
    }

    public boolean interpret() {
        int rel1 = Integer.parseInt(term1.interpret().toString());
        int rel2 = Integer.parseInt(term2.interpret().toString());
                    
        switch (relOp) {
            case ">": return rel1 > rel2;
            case "<": return rel1 < rel2;
            case "<>": return rel1 != rel2;
            case "=": return rel1 == rel2;
            case ">=": return rel1 >= rel2;
            case "<=": return rel1 <= rel2;
        }
        return false;
        
    }
}

// Rule No. 267:<input> → <scan> <open_paren> <close_paren>
class INPUT {
    Scanner scan = new Scanner(System.in);

    public INPUT(Symbol lhs) {
        //
    }

    public Object interpret() {
        Object var = scan.nextLine();
        return var;
    }
}
// Rule No. 28:<output> → <print> <open_paren> <term> <close_paren>
class OUTPUT {
    TERM term;

    public OUTPUT(Symbol lhs) {
        term = TERM.construct(lhs.children.get(2));
    }

    public void interpret() {
        System.out.println(term.interpret());
    }
}
