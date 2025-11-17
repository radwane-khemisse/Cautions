import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CompanyService } from '../../services/company.service';
import { Company } from '../../models/company.model';

@Component({
  selector: 'app-companies',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './companies.component.html',
  styleUrls: ['./companies.component.css'],
})
export class CompaniesComponent implements OnInit {
  companies: Company[] = [];
  form: Company = { name: '', domain: '', ipoDate: '', currentPrice: undefined };
  loading = false;
  error = '';
  editingId?: number;

  constructor(private companyService: CompanyService) {}

  ngOnInit(): void {
    this.fetch();
  }

  fetch(): void {
    this.loading = true;
    this.error = '';
    this.companyService.list().subscribe({
      next: (data) => {
        this.companies = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Erreur de chargement';
        console.error(err);
        this.loading = false;
      },
    });
  }

  edit(company: Company): void {
    this.editingId = company.id;
    this.form = { ...company, ipoDate: company.ipoDate?.slice(0, 10) };
  }

  reset(): void {
    this.editingId = undefined;
    this.form = { name: '', domain: '', ipoDate: '', currentPrice: undefined };
    this.error = '';
  }

  submit(): void {
    if (!this.form.name) {
      this.error = 'Nom requis';
      return;
    }
    this.error = '';
    const payload: Company = { ...this.form };
    const action$ = this.editingId
      ? this.companyService.update(this.editingId, payload)
      : this.companyService.create(payload);
    action$.subscribe({
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
    this.companyService.delete(id).subscribe({
      next: () => this.fetch(),
      error: (err) => {
        this.error = 'Echec de suppression';
        console.error(err);
      },
    });
  }
}
