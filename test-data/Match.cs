using System;
using System.Collections.Generic;

namespace SportsLeague.Data.Models;

public partial class Match
{
    public int MatchId { get; set; }

    public DateTime MatchDate { get; set; }

    public string? Venue { get; set; }

    public int HomeTeamId { get; set; }

    public int AwayTeamId { get; set; }

    public int SeasonId { get; set; }

    public int? HomeTeamScore { get; set; }

    public int? AwayTeamScore { get; set; }

    public virtual Team AwayTeam { get; set; } = null!;

    public virtual Team HomeTeam { get; set; } = null!;

    public virtual ICollection<PlayerMatchStat> PlayerMatchStats { get; set; } = new List<PlayerMatchStat>();

    public virtual Season Season { get; set; } = null!;
}
