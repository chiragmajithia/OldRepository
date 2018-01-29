"""
Implementation of *regularized* linear classification/regression by
plug-and-play loss functions
"""

from numpy import *
from pylab import *

from binary import *
from gd import *

class LossFunction:
    def loss(self, Y, Yhat):
        """
        The true values are in the vector Y; the predicted values are
        in Yhat; compute the loss associated with these predictions.
        """

        util.raiseNotDefined()

    def lossGradient(self, X, Y, Yhat):
        """
        The inputs are in the matrix X, the true values are in the
        vector Y; the predicted values are in Yhat; compute the
        gradient of the loss associated with these predictions.
        """

        util.raiseNotDefined()


class SquaredLoss(LossFunction):
    """
    Squared loss is (1/2) * sum_n (y_n - y'_n)^2
    """

    def loss(self, Y, Yhat):
        """
        The true values are in the vector Y; the predicted values are
        in Yhat; compute the loss associated with these predictions.
        """

        return 0.5 * dot(Y - Yhat, Y - Yhat)


    def lossGradient(self, X, Y, Yhat):
        """
        The inputs are in the matrix X, the true values are in the
        vector Y; the predicted values are in Yhat; compute the
        gradient of the loss associated with these predictions.
        """

        return - sum((Y - Yhat) * X.T, axis=1)


class LogisticLoss(LossFunction):
    """
    Logistic loss is sum_n log(1 + exp(- y_n * y'_n))
    """

    def loss(self, Y, Yhat):
        """
        The true values are in the vector Y; the predicted values are
        in Yhat; compute the loss associated with these predictions.
        """

        return log(1+exp(-dot(Y,Yhat)))


    def lossGradient(self, X, Y, Yhat):
        """
        The inputs are in the matrix X, the true values are in the
        vector Y; the predicted values are in Yhat; compute the
        gradient of the loss associated with these predictions.
        """

        return -1/(1+exp(-dot(Y,Yhat)))*dot(Y,X.T)


class HingeLoss(LossFunction):
    """
    Hinge loss is sum_n max{ 0, 1 - y_n * y'_n }
    """

    def loss(self, Y, Yhat):
        """
        The true values are in the vector Y; the predicted values are
        in Yhat; compute the loss associated with these predictions.
        """

        #FIXME
        return max(0,1-dot(Y,Yhat))

    def lossGradient(self, X, Y, Yhat):
        """
        The inputs are in the matrix X, the true values are in the
        vector Y; the predicted values are in Yhat; compute the
        gradient of the loss associated with these predictions.
        """

        #FIXME
        return [max(0,i) for i in -dot(Y,X)]


class LinearClassifier(BinaryClassifier):
    """
    This class defines an arbitrary linear classifier parameterized by
    a loss function and a ||w||^2 regularizer.
    """

    def __init__(self, opts):
        """
        Initialize the classifier.  Like perceptron, we need to start
        out with a weight vector; unlike perceptron, we'll leave off
        the bias.  Also, we are not online, so we implement that full
        train method.
        """

        # remember the options
        self.opts = opts

        # just call reset
        self.reset()

    def reset(self):
        self.weights = 0

    def online(self):
        """
        We're not online
        """
        return False

    def __repr__(self):
        """
        Return a string representation of the tree
        """
        return    "w=" + repr(self.weights)

    def predict(self, X):
        """
        X is a vector that we're supposed to make a prediction about.
        Our return value should be the margin at this point.
        Semantically, a return value <0 means class -1 and a return
        value >=0 means class +1
        """

        if type(self.weights) == int:
            return 0
        else:
            return dot(self.weights, X)

    def getRepresentation(self):
        """
        Return the weights
        """
        return self.weights

    def train(self, X, Y):
        """
        Train a linear model using gradient descent, based on code in
        module gd.
        """

        # get the relevant options
        lossFn   = self.opts['lossFunction']         # loss function to optimize
        lambd    = self.opts['lambda']               # regularizer is (lambd / 2) * ||w||^2
        numIter  = self.opts['numIter']              # how many iterations of gd to run
        stepSize = self.opts['stepSize']             # what should be our GD step size?

        # Set initial weights as array of zeros
        self.weights = zeros(X.T.shape[0])

        # define our objective function based on loss, lambd and (X,Y)
        #FIXME
        def func(w):
            # should compute obj = loss(w) + (lambd/2) * norm(w)^2
            Yhat = dot(w,X.T)
            
            obj  = lossFn.loss(Y,Yhat) + (lambd/2)*power(norm(w),2)

            # return the objective
            return obj

        # define our gradient function based on loss, lambd and (X,Y)
        #FIXME
        def grad(w):
            # should compute gr = grad(w) + lambd * w
            Yhat = dot(w,X.T)

            gr = lossFn.lossGradient(X,Y,Yhat) + lambd*w

            return gr

        # run gradient descent; our initial point will just be our
        # weight vector
        w, trajectory = gd(func, grad, self.weights, numIter, stepSize)

        # store the weights and trajectory
        self.weights = w
        self.trajectory = trajectory
