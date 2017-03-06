#include <string.h>
#include <stdlib.h>

#include "dberror.h"
#include "record_mgr.h"
#include "expr.h"
#include "tables.h"

// implementations
RC 
valueEquals (Value *left, Value *right, Value *result)
{
  if(left->dt != right->dt)
    return RC_RM_COMPARE_VALUE_OF_DIFFERENT_DATATYPE;

  result->dt = DT_BOOL;
  
  switch(left->dt) {
  case DT_INT:
    result->v.boolV = (left->v.intV == right->v.intV);
    break;
  case DT_FLOAT:
    result->v.boolV = (left->v.floatV == right->v.floatV);
    break;
  case DT_BOOL:
    result->v.boolV = (left->v.boolV == right->v.boolV);
    break;
  case DT_STRING:
    result->v.boolV = (strcmp(left->v.stringV, right->v.stringV) == 0);
    break;
  }

  return RC_OK;
}

RC 
valueSmaller (Value *left, Value *right, Value *result)
{
  if(left->dt != right->dt)
    return RC_RM_COMPARE_VALUE_OF_DIFFERENT_DATATYPE;

  result->dt = DT_BOOL;
  
  switch(left->dt) {
  case DT_INT:
    result->v.boolV = (left->v.intV < right->v.intV);
    break;
  case DT_FLOAT:
    result->v.boolV = (left->v.floatV < right->v.floatV);
    break;
  case DT_BOOL:
    result->v.boolV = (left->v.boolV < right->v.boolV);
  case DT_STRING:
    result->v.boolV =(strcmp(left->v.stringV, right->v.stringV) < 0);
    break;
  }

  return RC_OK;
}

RC 
boolNot (Value *input, Value *result)
{
  if (input->dt != DT_BOOL)
    return RC_RM_BOOLEAN_EXPR_ARG_IS_NOT_BOOLEAN;
  result->dt = DT_BOOL;
  result->v.boolV = !(input->v.boolV);

  return RC_OK;
}

RC
boolAnd (Value *left, Value *right, Value *result)
{
  if (left->dt != DT_BOOL || right->dt != DT_BOOL)
    return RC_RM_BOOLEAN_EXPR_ARG_IS_NOT_BOOLEAN;
  result->v.boolV = (left->v.boolV && right->v.boolV);

  return RC_OK;
}

RC
boolOr (Value *left, Value *right, Value *result)
{
  if (left->dt != DT_BOOL || right->dt != DT_BOOL)
    return RC_RM_BOOLEAN_EXPR_ARG_IS_NOT_BOOLEAN;
  result->v.boolV = (left->v.boolV || right->v.boolV);

  return RC_OK;
}

RC
evalExpr (Record *record, Schema *schema, Expr *expr, Value **result)
{
  Value *leftInput;
  Value *righttInput;
  MAKE_VALUE(*result, DT_INT, -1);

  switch(expr->type)
    {
    case EXPR_OP:
      {
      Operator *op = expr->expr.op;
      bool opType = (op->type != OP_BOOL_NOT);
       
      CHECK(evalExpr(record, schema, op->args[0], &leftInput));
      if (opType)
	CHECK(evalExpr(record, schema, op->args[1], &righttInput));

      switch(op->type) 
	{
	case OP_BOOL_NOT:
	  CHECK(boolNot(leftInput, *result));
	  break;
	case OP_BOOL_AND:
	  CHECK(boolAnd(leftInput, righttInput, *result));
	  break;
	case OP_BOOL_OR:
	  CHECK(boolOr(leftInput, righttInput, *result));
	  break;
	case OP_COMP_EQUAL:
	  CHECK(valueEquals(leftInput, righttInput, *result));
	  break;
	case OP_COMP_SMALLER:
	  CHECK(valueSmaller(leftInput, righttInput, *result));
	  break;
	default:
	  break;
	}

      freeVal(leftInput);
      if (opType)
	freeVal(righttInput);
      }
      break;
    case EXPR_CONST:
      CPVAL(*result,expr->expr.cons);
      break;
    case EXPR_ATTRREF:
      free(*result);
      CHECK(getAttr(record, schema, expr->expr.attrRef, result));
      break;
    }

  return RC_OK;
}

RC
freeExpr (Expr *expr)
{
  switch(expr->type) 
    {
    case EXPR_OP:
      {
      Operator *op = expr->expr.op;
      switch(op->type) 
	{
	case OP_BOOL_NOT:
	  freeExpr(op->args[0]);
	  break;
	default:
	  freeExpr(op->args[0]);
	  freeExpr(op->args[1]);
	  break;
	}
      free(op->args);
      }
      break;
    case EXPR_CONST:
      freeVal(expr->expr.cons);
      break;
    case EXPR_ATTRREF:
      break;
    }
  free(expr);
  
  return RC_OK;
}

void 
freeVal (Value *val)
{
  if (val->dt == DT_STRING)
    free(val->v.stringV);
  free(val);
}
