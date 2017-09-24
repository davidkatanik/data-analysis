/**
 * 
 */
package cv09;

import java.util.List;

import Jama.EigenvalueDecomposition;

/**
 * @author David Katanik
 *
 * @version 1.0 from 15. 4. 2017
 *
 */
public class Main {

	public static void main(String[] args) {
		List<String> readDataFromFile = Utils.readDataFromFile();
		Matrix loaded = new Matrix(readDataFromFile);

		System.out.println("Loaded");
		loaded.printMatrix();

		System.out.println("DeltaDiagonal");
		Matrix deltaDiagonal = getDiagonalMatrix(loaded);
		deltaDiagonal.printMatrix();

		System.out.println("Normalized");
		Matrix normalized = getNormalizedMatrix(loaded, deltaDiagonal);
		normalized.printMatrix();

		System.out.println("Laplacian");
		Matrix laplacian = getLaplacianMatrix(loaded, deltaDiagonal);
		laplacian.printMatrix();

		System.out.println("Eigen Values");
		printEigenValues(normalized);

		System.out.println();
	}

	private static Matrix getDiagonalMatrix(Matrix loaded) {
		Matrix result = new Matrix(loaded.getSize());

		for (int i = 0; i < result.getSize(); i++) {
			double sumOfRow = 0d;
			for (int j = 0; j < result.getSize(); j++) {
				double actual = loaded.getValue(i, j);
				sumOfRow += actual;
				// result.setValue(i, j, actual);
			}
			result.setValue(i, i, sumOfRow);
		}

		return result;
	}

	private static Matrix getNormalizedMatrix(Matrix loaded, Matrix deltaDiagonal) {
		Matrix result = new Matrix(loaded.getSize());

		for (int i = 0; i < result.getSize(); i++) {
			double diagonal = deltaDiagonal.getValue(i, i);
			if (diagonal != 0) {
				for (int j = 0; j < result.getSize(); j++) {
					double actual = loaded.getValue(i, j);
					result.setValue(i, j, actual / diagonal);
				}
			}
		}

		return result;
	}

	private static Matrix getLaplacianMatrix(Matrix loaded, Matrix deltaDiagonal) {
		Matrix result = new Matrix(loaded.getSize());

		for (int i = 0; i < result.getSize(); i++) {
			for (int j = 0; j < result.getSize(); j++) {
				double actualDeltaDiagonal = deltaDiagonal.getValue(i, j);
				double actualLoaded = loaded.getValue(i, j);
				result.setValue(i, j, actualDeltaDiagonal - actualLoaded);
			}
		}

		return result;
	}

	private static void printEigenValues(Matrix normalized) {

		Jama.Matrix m = mapMatrix(normalized);

		EigenvalueDecomposition eigen = m.eig();

		double[] realPart = eigen.getRealEigenvalues();
		double[] imagPart = eigen.getImagEigenvalues();

		for (int i = 0; i < realPart.length-1; i++) {
//			System.out.println("Eigen Value " + i + " is " + "[" + realPart[i] + ", " + +imagPart[i] + "]");
			System.out.println("Eigen Value " + i + " is " + realPart[i]);
		}
	}

	private static Jama.Matrix mapMatrix(Matrix normalized) {
		Jama.Matrix result = new Jama.Matrix(normalized.getSize(), normalized.getSize());

		for (int i = 0; i < normalized.getSize(); i++) {
			for (int j = 0; j < normalized.getSize(); j++) {
				result.set(i, j, normalized.getValue(i, j));
			}

		}
		return result;
	}

}
