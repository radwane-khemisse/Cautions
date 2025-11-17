import { Routes } from '@angular/router';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { CompaniesComponent } from './pages/companies/companies.component';
import { StocksComponent } from './pages/stocks/stocks.component';

export const routes: Routes = [
  { path: '', component: DashboardComponent },
  { path: 'companies', component: CompaniesComponent },
  { path: 'stocks', component: StocksComponent },
  { path: '**', redirectTo: '' },
];
