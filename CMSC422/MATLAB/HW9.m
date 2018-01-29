
syms x
h = (exp(x)-exp(-x))/(exp(x)+exp(-x))
w1 = [1,-2,-3]
w2 = [0,3,1]
v = [2,-1]
in = [-2,1,-1]

h1 = w1 * in'
h1 = tanh(h1)

h2 = w2 * in'
h2 = tanh(h2)

y = v(1)*h1 + v(2)*h2
v_ = v - 0.1*1*[h1 h2]
w1_ = w1' - (0.1 * v *[1-h1^2;1-h2^2]*in)'