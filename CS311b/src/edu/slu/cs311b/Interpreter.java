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
            case 10:
                return new STMT_6(sym);
            case 11:
                return new STMT_7(sym);
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
        assignment = ASSIGNMENT.construct(lhs.children.get(0));
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
// Rule No. 8:<stmt> → <math_expr>
class STMT_4 extends STMT {

    MATH_EXPR math_expr;

    public STMT_4(Symbol lhs) {
        math_expr = new MATH_EXPR(lhs.children.get(0));
    }

    @Override
    public void interpret() {
        System.out.println("math_expr.interpret()");
        math_expr.interpret();
    }
}
// Rule No. 9:<stmt> → <while_stmt>
class STMT_5 extends STMT {

    WHILE_STMT while_stmt;

    public STMT_5(Symbol lhs) {
        while_stmt = new WHILE_STMT(lhs.children.get(0));
    }

    @Override
    public void interpret() {
        System.out.println("while_stmt.interpret()");
        while_stmt.interpret();
    }
}
// Rule No. 10:<stmt> → <input>
class STMT_6 extends STMT {

    INPUT input;

    public STMT_6(Symbol lhs) {
        input = new INPUT(lhs.children.get(0));
    }

    @Override
    public void interpret() {
        System.out.println("input.interpret()");
        input.interpret();
    }
}
// Rule No. 11:<stmt> → <output>
class STMT_7 extends STMT {

    OUTPUT output;

    public STMT_7(Symbol lhs) {
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
            case 12:
                return new TERM_1(sym);
            case 13:
                return new TERM_2(sym);
            default:
                return null;
        }
    }
    public abstract Object interpret();
}
// Rule No. 12:<term> → <iden>
class TERM_1 extends TERM {

    Variable iden;

    public TERM_1(Symbol lhs) {
        iden = Variable.symbolTable.get(lhs.children.get(0).lexeme);
    }

    @Override
    public Object interpret() {
        return iden.value;
    }
}
// Rule No. 13:<term> → <value>
class TERM_2 extends TERM {

    String const1;

    public TERM_2(Symbol lhs) {
        const1 = lhs.children.get(0).lexeme;
    }

    @Override
    public Object interpret() {
        return Integer.parseInt(const1);
    }
}

// Rule No. 14:<declaration> → <var> <initVal>
class DECLARATION {
    INITVAL initVal;
    
    public DECLARATION(Symbol lhs){
        initVal = INITVAL.construct(lhs.children.get(1));
    }
    
    public void interpret() {
        initVal.interpret();
    }
}

abstract class INITVAL {
    public static INITVAL construct(Symbol sym){
        switch (sym.ruleNo){
            case 15:
                return new INITVAL_1(sym);
            case 16:
                return new INITVAL_2(sym);
            default:
                return null;
        }
    }
    public abstract void interpret();
}
// Rule No. 15:<initVal> → <iden> <colon> <type> <assignOp> <expr>
class INITVAL_1 extends INITVAL {
    String iden;
    EXPR expr;

    public INITVAL_1(Symbol lhs) {
        iden = lhs.children.get(0).lexeme;
        expr = new EXPR(lhs.children.get(4));
    }

    @Override
    public void interpret() {
        Variable var = Variable.symbolTable.get(iden);
        var.value = expr.interpret();
    }
}
// Rule No. 16:<initVal> → ε
class INITVAL_2 extends INITVAL {


    public INITVAL_2(Symbol lhs) {
        // rhs is EPSILON - default to null(???)
    }

    @Override
    public void interpret() {
    }
}
abstract class ASSIGNMENT {
    public static ASSIGNMENT construct(Symbol sym){
        switch (sym.ruleNo){
            case 17:
                return new ASSIGNMENT_1(sym);
            case 18:
                return new ASSIGNMENT_2(sym);
            default:
                return null;
        }
    }
    public abstract void interpret();
}
// Rule No. 17:<assignment> → <iden> <assignOp> <expr>
class ASSIGNMENT_1 extends ASSIGNMENT{

    String iden;
    EXPR expr;

    public ASSIGNMENT_1(Symbol lhs) {
        iden = lhs.children.get(0).lexeme;
        expr = new EXPR(lhs.children.get(2));
    }
    @Override
    public void interpret() {
        Variable var = Variable.symbolTable.get(iden);
        var.value = expr.interpret();

    }
}
// Rule No. 18:<assignment> → <iden> <assignOp> <input>
class ASSIGNMENT_2 extends ASSIGNMENT{

    String iden;
    INPUT input;


    public ASSIGNMENT_2(Symbol lhs) {
        iden = lhs.children.get(0).lexeme;
        input = new INPUT(lhs.children.get(2));
    }
    @Override
    public void interpret() {
        Variable var = Variable.symbolTable.get(iden);
        var.value = input.interpret();


    }
}

// Rule No. 19:<expr> → <expr2> <expr'>
// Rule No. 20:<expr'> → <arithOp> <expr2> <expr'>
// Rule No. 21:<expr'> → ε
// Rule No. 22:<expr2> → <term> <expr2'>
// Rule No. 23:<expr2'> → <arithOp> <term> <expr2'>
// Rule No. 24:<expr2'> → ε

class EXPR {
    // DO NOT DELETE.
    // Use this class can evaluation arithmetic, relational or logical expressions
    // Just change the case labels/selectable segments in the switch statement to suit your programming langauge

    // See usage in LINE 192 and LINE 203 in MyInterpreter.java for your reference
       
    
    LinkedList<Symbol> expression;
    Stack<Integer> operands = new Stack();

    public EXPR(Symbol lhs) {
        expression = Expression.getExpression(lhs);
    }

    public int interpret() {
        Variable var;
        int result = 0;

        while (!expression.isEmpty()) {
            Symbol sym = expression.removeFirst();

            switch (sym.type) {
                // sym is an operand
                case "<value>":
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

// Rule No. 25:<if_stmt> → <if> <colon> <open_paren> <rel_expr> <close_paren> <then> <stmt_list'> <ifEnd>
class IF_STMT {
    STMT_LIST_PRIME stmt_list_prime;
    REL_EXPR rel_expr;
    
    public IF_STMT(Symbol lhs) {
        rel_expr = new REL_EXPR(lhs.children.get(3));
        stmt_list_prime = STMT_LIST_PRIME.construct(lhs.children.get(6));
    }

    public void interpret() {
        rel_expr.interpret();
        stmt_list_prime.interpret();
    }
}
// Rule No. 26:<while_stmt> → <while> <colon> <open_paren> <rel_expr> <close_paren> <stmt_list'> <whileEnd>
class WHILE_STMT{
    STMT_LIST_PRIME stmt_list_prime;
    REL_EXPR rel_expr;
    
    public WHILE_STMT(Symbol lhs) {
        rel_expr = new REL_EXPR(lhs.children.get(3));
        stmt_list_prime = STMT_LIST_PRIME.construct(lhs.children.get(6));
    }

    public void interpret() {
        rel_expr.interpret();
        stmt_list_prime.interpret();
    }
}
// Rule No. 27:<math_expr> → <term> <arithOp> <term>
class MATH_EXPR {
    EXPR term1;
    String arithOp;
    EXPR term2;
    
    
    public MATH_EXPR(Symbol lhs) {
        term1 = new EXPR(lhs.children.get(0));
        arithOp = lhs.children.get(1).lexeme;
        term2 = new EXPR(lhs.children.get(2));
    }

    public Object interpret() {
        
        int temp1 = term1.interpret();
        int temp2 = term2.interpret();
            
            switch (arithOp) {
                case "+": return temp1 + temp2;
                case "-": return temp1 - temp2;
                case "*": return temp1 - temp2;
                case "/": return temp1 / temp2;              
            }
        return false;
    }
}


// Rule No. 28:<rel_expr> → <term> <relOp> <term>
class REL_EXPR  {
    TERM term1;
    String relOp;
    TERM term2;
    Symbol lhs;
    
    public REL_EXPR(Symbol lhs) {
        this.lhs = lhs;
        term1 = TERM.construct(lhs.children.get(0));
        relOp = lhs.children.get(1).lexeme;
        term2 = TERM.construct(lhs.children.get(2));
    }

    public boolean interpret() {
        Object temp1;
        Object temp2;
        switch (lhs.children.get(0).type) {
            case "<value>":
                if (term1.interpret() instanceof Integer && term2.interpret() instanceof Integer){
                    temp1 = (int)term1.interpret();
                    temp2 = (int)term2.interpret();
                    
                    switch (relOp) {
                        case ">": return temp1 > temp2;
                        case "<": return temp1 < temp2;
                        case "<>": return temp1 != temp2;
                        case "=": return temp1 == temp2;
                        case ">=": return temp1 >= temp2;
                        case "<=": return temp1 <= temp2;
                    }
                } else if(term1.interpret() instanceof Double && term2.interpret() instanceof Double){
                    temp1 = new Double(term1.interpret().toString());
                    temp2 = new Double(term2.interpret().toString());
                    
                    switch (relOp) {
                        case ">": return temp1 > temp2;
                        case "<": return temp1 < temp2;
                        case "<>": return temp1 != temp2;
                        case "=": return temp1 == temp2;
                        case ">=": return temp1 >= temp2;
                        case "<=": return temp1 <= temp2;
                    }
                }   break;
            case "<iden>":
                switch (relOp) {
                    
                    case ">": return temp1 > temp2;
                    case "<": return temp1 < temp2;
                    case "<>": return temp1 != temp2;
                    case "=": return temp1 == temp2;
                    case ">=": return temp1 >= temp2;
                    case "<=": return temp1 <= temp2;
                }
            default:
                return false;
        }
        return false;
        
    }
}

// Rule No. 29:<input> → <scan> <open_paren> <close_paren>
class INPUT {
    Scanner scan = new Scanner(System.in);

    public INPUT(Symbol lhs) {
        
    }

    public Object interpret() {
        Object var = scan.nextLine();
        return var;
    }
}
// Rule No. 30:<output> → <print> <open_paren> <term> <close_paren>
class OUTPUT {
    TERM term;

    public OUTPUT(Symbol lhs) {
        term = TERM.construct(lhs.children.get(2));
    }

    public void interpret() {
        System.out.print(term.interpret());
    }
}



