可以在运行时修改System.out的输出流
<pre>
try {
    OutputStream outputStream = new FileOutputStream("system-out.log");
    System.setOut(new PrintStream(outputStream));
    System.out.println("File opened...");

} catch (IOException e) {
    System.err.println("File opening failed:");
    e.printStackTrace();
}
</pre>