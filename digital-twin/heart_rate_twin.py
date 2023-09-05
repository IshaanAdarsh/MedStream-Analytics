class HeartRateMonitorDigitalTwin:
       def __init__(self, patient_id):
           self.patient_id = patient_id
           self.heart_rate = 0
           self.status = "Normal"

       def update_heart_rate(self, new_rate):
           self.heart_rate = new_rate
