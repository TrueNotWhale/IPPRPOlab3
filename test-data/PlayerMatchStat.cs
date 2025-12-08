using System;
using System.Collections.Generic;

namespace SportsLeague.Data.Models;

public partial class PlayerMatchStat
{
    public long StatId { get; set; }
    public int MatchId { get; set; }
    public int PlayerId { get; set; }
    public int? GoalsScored { get; set; }
    public int? Assists { get; set; } // Голевые передачи

    // Новые поля
    public int Passes { get; set; } // Всего передач
    public decimal DistanceKM { get; set; } // Дистанция
    public int Fouls { get; set; } // Фолы
    public bool WasOnField { get; set; } // Выходил ли на поле

    public virtual Match Match { get; set; } = null!;
    public virtual Player Player { get; set; } = null!;
}