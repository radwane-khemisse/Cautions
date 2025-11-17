import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CompanyService } from '../../services/company.service';
import { StockService } from '../../services/stock.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent implements OnInit {
  companyCount = 0;
  stockCount = 0;
  loading = true;

  constructor(private companyService: CompanyService, private stockService: StockService) {}

  ngOnInit(): void {
    this.refresh();
  }

  refresh(): void {
        this.loading = true;
        this.companyService.list().subscribe({
          next: (c) => {
            this.companyCount = c.length;
            this.loading = false;
          },
          error: () => (this.loading = false),
        });
        this.stockService.list().subscribe({
          next: (s) => (this.stockCount = s.length),
          error: () => {},
        });
  }
}
