package concurrencyinpractice.chapter02;

import concurrencyinpractice.annotation.GuardedBy;
import concurrencyinpractice.annotation.ThreadSafe;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class SynchronizedFactorizer extends HttpServlet {

	@GuardedBy("this")
    private BigInteger lastNumber;

	@GuardedBy("this")
	private BigInteger[] lastFactors;

	@Override
	public synchronized void service(ServletRequest request,
			ServletResponse response) throws ServletException, IOException {
		BigInteger i = extractFromRequest(request);
		if (i.equals(lastNumber)) {
			encodeIntoResponse(response, lastFactors);
		} else {
			BigInteger[] factors = factor(i);
			lastNumber = i;
			lastFactors = factors;
			encodeIntoResponse(response, factors);
		}
	}

	private void encodeIntoResponse(ServletResponse response,
			BigInteger[] factors) {
		// TODO Auto-generated method stub

	}

	private BigInteger[] factor(BigInteger i) {
		// TODO Auto-generated method stub
		return null;
	}

	private BigInteger extractFromRequest(ServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
}