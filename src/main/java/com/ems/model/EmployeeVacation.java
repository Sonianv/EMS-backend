package com.ems.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
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
        PENDING, REJECTED, ACCEPTED
    }

}
