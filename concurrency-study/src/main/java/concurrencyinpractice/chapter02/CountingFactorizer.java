package concurrencyinpractice.chapter02;

import concurrencyinpractice.annotation.ThreadSafe;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicLong;

@ThreadSafe
public class CountingFactorizer extends HttpServlet {

	private final AtomicLong count = new AtomicLong(0);

	public long getCount() {
        return count.get();
	}

	@Override
	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		BigInteger i = extractFromRequest(request);
		BigInteger[] factors = factor(i);
		count.incrementAndGet();
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