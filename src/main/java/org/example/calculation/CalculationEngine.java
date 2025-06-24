package org.example.calculation;

import org.example.model.ACSPLUSInput;

/**
 * CalculationEngine defines the contract for performing HVAC calculations
 * based on the provided inputs.
 */
public interface CalculationEngine {
    /**
     * Execute HVAC calculations and return computed results.
     *
     * @param input the set of parameters required for calculation
     * @return a CalculationResult containing all computed values
     */
    CalculationResult calculate(ACSPLUSInput input);
}