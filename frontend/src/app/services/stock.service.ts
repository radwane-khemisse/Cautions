import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Stock } from '../models/stock.model';

@Injectable({ providedIn: 'root' })
export class StockService {
  // Use gateway if available; otherwise point directly to stock-service (default here is direct)
  private readonly baseUrl = 'http://localhost:8082/stocks';

  constructor(private http: HttpClient) {}

  list(): Observable<Stock[]> {
    return this.http.get<Stock[]>(this.baseUrl);
  }

  get(id: number): Observable<Stock> {
    return this.http.get<Stock>(`${this.baseUrl}/${id}`);
  }

  create(payload: Stock): Observable<Stock> {
    return this.http.post<Stock>(this.baseUrl, payload);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
