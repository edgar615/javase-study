package concurrencyinpractice.chapter02;

import concurrencyinpractice.annotation.NotThreadSafe;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.math.BigInteger;

@NotThreadSafe
public class UnsafeCountingFactorizer extends HttpServlet {

	private long count = 0;

	public long getCount() {
        return count;
	}

	@Override
	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		BigInteger i = extractFromRequest(request);
		BigInteger[] factors = factor(i);
		++count;
		encodeIntoResponse(response, factors);
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