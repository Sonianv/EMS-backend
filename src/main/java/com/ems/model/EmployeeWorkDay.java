package com.ems.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee_work_days")
public class EmployeeWorkDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;
    @Column(name = "day")
    private LocalDate day;
    @Column(name = "start", nullable = false)
    private LocalTime start;
    @Column(name = "end")
    private LocalTime end;
    @Column(name = "break_time")
    private float breakTime;
    @Enumerated(EnumType.STRING)
    @Column
    private Status status;
    @Column(name = "worked_hours")
    private Double workedHours;

    public enum Status {
        BELOW_EXPECTED, AS_EXPECTED, ABOVE_EXPECTED, IN_PROGRESS
    }

    public void update(EmployeeWorkDay employeeWorkDay) {
        this.day = employeeWorkDay.day;
        this.start = employeeWorkDay.getStart();
        this.end = employeeWorkDay.getEnd();
        this.breakTime = employeeWorkDay.getBreakTime();
        this.status = employeeWorkDay.getStatus();
        this.workedHours = employeeWorkDay.getWorkedHours();
    }
}
