package kr.co.wmhr.common.advice;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import kr.co.wmhr.common.transaction.DataSourceTransactionManager;

public class TransactionAdvice implements MethodInterceptor {
	   private DataSourceTransactionManager dataSourceTransactionManager;

	   public void setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager) {
	      this.dataSourceTransactionManager = dataSourceTransactionManager;
	   }

	   protected final Log logger = LogFactory.getLog(getClass());

	   @Override
	   public Object invoke(MethodInvocation invocation) throws Throwable {
	      if (logger.isDebugEnabled()) {
	         logger.debug(invocation.getThis().getClass().getSimpleName() + "." + invocation.getMethod().getName()
	               + "() start");
	      }
	      Object reVal = null;
	      try {
	         dataSourceTransactionManager.beginTransaction();
	         reVal = invocation.proceed();
	         dataSourceTransactionManager.commitTransaction();

	         return reVal;
	      } catch (Exception e) {
	         dataSourceTransactionManager.rollbackTransaction();
	         logger.fatal(e.getMessage());
	         throw e;
	      } finally {
	         if (logger.isDebugEnabled()) {
	            logger.debug(invocation.getThis().getClass().getSimpleName() + "." + invocation.getMethod().getName() + "() end");
	         }
	      }
	   }
}
