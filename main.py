from digital_twin.heart_rate_twin import HeartRateMonitorDigitalTwin
from producer import simulate_heart_rate

if __name__ == "__main__":
    patient_id = 123  # Replace with an actual patient ID
    heart_rate_twin = HeartRateMonitorDigitalTwin(patient_id)
    simulate_heart_rate(heart_rate_twin)
