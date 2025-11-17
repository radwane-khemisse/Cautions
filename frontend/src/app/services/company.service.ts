import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Company } from '../models/company.model';

@Injectable({ providedIn: 'root' })
export class CompanyService {
  // Use gateway if available; otherwise point directly to company-service (default here is direct)
  private readonly baseUrl = 'http://localhost:8081/companies';

  constructor(private http: HttpClient) {}

  list(): Observable<Company[]> {
    return this.http.get<Company[]>(this.baseUrl);
  }

  get(id: number): Observable<Company> {
    return this.http.get<Company>(`${this.baseUrl}/${id}`);
  }

  create(payload: Company): Observable<Company> {
    return this.http.post<Company>(this.baseUrl, payload);
  }

  update(id: number, payload: Company): Observable<Company> {
    return this.http.put<Company>(`${this.baseUrl}/${id}`, payload);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  updatePrice(id: number, price: number): Observable<Company> {
    return this.http.put<Company>(`${this.baseUrl}/${id}/price`, { currentPrice: price });
  }
}
