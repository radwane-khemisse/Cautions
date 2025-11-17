import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { StockService } from '../../services/stock.service';
import { Stock } from '../../models/stock.model';

@Component({
  selector: 'app-stocks',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './stocks.component.html',
  styleUrls: ['./stocks.component.css'],
})
export class StocksComponent implements OnInit {
  stocks: Stock[] = [];
  form: Stock = { companyId: undefined, date: '', openValue: undefined, highValue: undefined, lowValue: undefined, closeValue: undefined, volume: undefined };
  loading = false;
  error = '';

  constructor(private stockService: StockService) {}

  ngOnInit(): void {
    this.fetch();
  }

  fetch(): void {
    this.loading = true;
    this.error = '';
    this.stockService.list().subscribe({
      next: (data) => {
        this.stocks = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Erreur de chargement';
        console.error(err);
        this.loading = false;
      },
    });
  }

  reset(): void {
    this.form = { companyId: undefined, date: '', openValue: undefined, highValue: undefined, lowValue: undefined, closeValue: undefined, volume: undefined };
    this.error = '';
  }

  submit(): void {
    if (!this.form.companyId) {
      this.error = 'companyId requis';
      return;
    }
    this.error = '';
    this.stockService.create(this.form).subscribe({
      next: () => {
        this.reset();
        this.fetch();
      },
      error: (err) => {
        this.error = 'Echec de sauvegarde';
        console.error(err);
      },
    });
  }

  remove(id?: number): void {
    if (!id) return;
    this.stockService.delete(id).subscribe({
      next: () => this.fetch(),
      error: (err) => {
        this.error = 'Echec de suppression';
        console.error(err);
      },
    });
  }
}
