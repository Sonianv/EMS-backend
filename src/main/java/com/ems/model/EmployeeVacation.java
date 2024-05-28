package com.ems.model;

import com.ems.error.ResourceNotFoundException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Arrays;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee_vacations")
public class EmployeeVacation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;
    @Enumerated(EnumType.STRING)
    private Status status;

    public void update(EmployeeVacation employeeVacation) {
        this.startDate = employeeVacation.getStartDate();
        this.endDate = employeeVacation.getEndDate();
        this.status = employeeVacation.getStatus();
    }

    public enum Status {
        PENDING, REJECTED, ACCEPTED;

        public static Status findByName(String name) {
            return Arrays.stream(values()).filter(v -> v.name().equals(name)).findFirst().orElseThrow(() -> new ResourceNotFoundException("Unknown vacation status"));
        }
    }

}
